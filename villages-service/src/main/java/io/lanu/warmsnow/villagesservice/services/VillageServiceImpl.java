package io.lanu.warmsnow.villagesservice.services;

import io.lanu.warmsnow.common_models.FieldType;
import io.lanu.warmsnow.common_models.models.Field;
import io.lanu.warmsnow.common_models.models.FieldTaskModel;
import io.lanu.warmsnow.common_models.models.Warehouse;
import io.lanu.warmsnow.common_models.requests.FieldUpgradeRequest;
import io.lanu.warmsnow.common_models.requests.NewVillageRequest;
import io.lanu.warmsnow.templates.templates_client.dto.VillageDto;
import io.lanu.warmsnow.villagesservice.clients.ConstructionsServiceFeignClient;
import io.lanu.warmsnow.villagesservice.clients.TemplatesServiceFeignClient;
import io.lanu.warmsnow.villagesservice.entities.VillageEntity;
import io.lanu.warmsnow.villagesservice.models.FieldTask;
import io.lanu.warmsnow.villagesservice.models.TaskExecution;
import io.lanu.warmsnow.villagesservice.repositories.VillageRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class VillageServiceImpl implements VillageService {

    private VillageRepository villageRepository;
    private TemplatesServiceFeignClient templatesFeignClient;
    private final ConstructionsServiceFeignClient constructionsClient;
    private final ModelMapper MAPPER = new ModelMapper();

    public VillageServiceImpl(VillageRepository villageRepository, TemplatesServiceFeignClient templatesFeignClient, ConstructionsServiceFeignClient constructionsClient) {
        this.villageRepository = villageRepository;
        this.templatesFeignClient = templatesFeignClient;
        this.constructionsClient = constructionsClient;
    }

    @Override
    public VillageEntity createVillage(NewVillageRequest newVillageRequest) {
        // get the village template from Template service
        VillageEntity villageTemplate = templatesFeignClient.getVillageByType(newVillageRequest.getVillageType());
        //  set some properties
        villageTemplate.setAccountId(newVillageRequest.getAccountId());
        villageTemplate.setX(newVillageRequest.getX());
        villageTemplate.setY(newVillageRequest.getY());
        // save the village entity to DB
        VillageEntity newVillage = villageRepository.save(villageTemplate);
        log.info("New village has been created");
        return newVillage;
    }

    @Override
    public VillageDto getVillageById(String id) {
        // fetch the Village
        VillageEntity villageEntity = villageRepository.findById(id).orElseThrow();

        // fetch construction tasks
        List<FieldTask> fieldTasks = constructionsClient.getTasksByVillageId(id);

        // check weather is field under upgrade if so change status
        fieldTasks.forEach(fieldTask -> {
            Field f = villageEntity.getFields().get(fieldTask.getField().getPosition());
            f.setUnderUpgrade(true);
        });

        // fetch army tasks
        // List<ArmyTask> armyTasks = armyClient.getTasksByVillageId(id);

        // combine all tasks

        List<TaskExecution> taskExecutions = new ArrayList<>();
        taskExecutions.addAll(fieldTasks);
        // taskExecutions.addAll(armyTasks);

        calculateProducedGoods(villageEntity, taskExecutions);
        checkFieldsUpgradable(villageEntity);
        recalculateTasksTimeLeft(villageEntity);

        // save VillageEntity after all counting
        villageRepository.save(villageEntity);
        return MAPPER.map(villageEntity, VillageDto.class);
    }

    @Override
    public void scheduleFieldUpgrade(FieldUpgradeRequest request) {
        // get the village for upgrade
        VillageEntity villageEntity = villageRepository.findById(request.getVillageId()).orElseThrow();
        // get the field for upgrade in that village
        Field field = villageEntity.getFields().get(request.getField().getPosition());
        field.setUnderUpgrade(true);
        // subtract needed resources for upgrade the Field
        Warehouse warehouse = villageEntity.getWarehouse();
        subtractResourcesFromWarehouse(warehouse, field.getResourcesToNextLevel());
        //schedule the task
        FieldTaskModel task = new FieldTaskModel(UUID.randomUUID().toString(),
                LocalDateTime.now().plus(field.getTimeToNextLevel(), ChronoUnit.SECONDS), field.getFieldType(),
                field.getPosition(), field.getLevel() + 1, field.getTimeToNextLevel());
        villageEntity.getTasks().add(task);
        log.info("Field upgrade scheduled.");
        // save the village to DB
        villageRepository.save(villageEntity);
    }

    private void recalculateTasksTimeLeft(VillageEntity villageEntity) {
        villageEntity.getTasks().forEach(taskModel ->
                taskModel.setTimeLeft(ChronoUnit.SECONDS.between(LocalDateTime.now(), taskModel.getExecution())));
    }

    private void checkFieldsUpgradable(VillageEntity villageEntity) {
        villageEntity.getFields()
                .forEach(field -> {
                    if (compareResources(field.getResourcesToNextLevel(), villageEntity.getWarehouse().getGoods())){
                        field.setAbleToUpgrade(true);
                    }else field.setAbleToUpgrade(false);
                });
    }

    private boolean compareResources(Map<FieldType, BigDecimal> need, Map<FieldType, BigDecimal> available){
        for (FieldType key : need.keySet()){
            if (available.get(key).compareTo(need.get(key)) < 0){
                return false;
            }
        }
        return true;
    }

    private void calculateProducedGoods(VillageEntity villageEntity, List<TaskExecution> taskExecution) {
        // filter all tasks that the village has with time before now
        List<TaskExecution> tasksList = taskExecution
                .stream()
                .filter(task -> task.getExecutionTime().isBefore(LocalDateTime.now()))
                .sorted(Comparator.comparing(TaskExecution::getExecutionTime))
                .collect(Collectors.toList());

        LocalDateTime modified = villageEntity.getModified();

        // if the village doesnt have any tasks
        if (tasksList.size() == 0) {
            calculate(villageEntity, villageEntity.getModified(), LocalDateTime.now());
        } else { // if the village does any tasks lastModified should be changed every task's completed time
            // calculate between each task which should be completed before now
            for (TaskExecution task : tasksList) {
                // recalculate warehouse leftovers
                calculate(villageEntity, modified, task.getExecutionTime());
                modified = task.getExecutionTime();
                task.executeTask(villageEntity);
            }
            // last calculate between last task and now
            calculate(villageEntity, modified, LocalDateTime.now());
        }
    }

    private void calculate(VillageEntity villageEntity, LocalDateTime lastModified, LocalDateTime untilTime){
        long durationFromLastModified = Duration
                .between(lastModified, untilTime).toMillis();

        Map<FieldType, Integer> producePerHour = villageEntity.getProducePerHour().getGoods();

        MathContext mc = new MathContext(3);
        BigDecimal wood =
                new BigDecimal((durationFromLastModified * (double) producePerHour.get(FieldType.WOOD)) / 3600000L, mc);
        BigDecimal clay =
                new BigDecimal((durationFromLastModified * (double) producePerHour.get(FieldType.CLAY)) / 3600000L, mc);
        BigDecimal iron =
                new BigDecimal((durationFromLastModified * (double) producePerHour.get(FieldType.IRON)) / 3600000L, mc);
        BigDecimal crop =
                new BigDecimal((durationFromLastModified * (double) producePerHour.get(FieldType.CROP)) / 3600000L, mc);

        Map<FieldType, BigDecimal> goods = villageEntity.getWarehouse().getGoods();
        goods.put(FieldType.WOOD, goods.get(FieldType.WOOD).add(wood));
        goods.put(FieldType.CLAY, goods.get(FieldType.CLAY).add(clay));
        goods.put(FieldType.IRON, goods.get(FieldType.IRON).add(iron));
        goods.put(FieldType.CROP, goods.get(FieldType.CROP).add(crop));

        log.info("Produced resources added to the Warehouse.");
    }

    private void subtractResourcesFromWarehouse(Warehouse warehouse, Map<FieldType, BigDecimal> resourcesToNextLevel) {
        warehouse.getGoods()
                .forEach((k, v) -> warehouse.getGoods().put(k, warehouse.getGoods().get(k).subtract(resourcesToNextLevel.get(k))));
    }

    /*private void upgradeField(VillageEntity villageEntity, FieldTaskModel fieldTaskModel) {
        // get new field from Templates service
        FieldDto upgradedFieldTemplate = templatesFeignClient
                .getFieldByLevelAndType(fieldTaskModel.getLevel(), fieldTaskModel.getFieldType());
        upgradedFieldTemplate.setPosition(fieldTaskModel.getPosition());
        Field upgradedField = MAPPER.map(upgradedFieldTemplate, Field.class);
        // calculate difference in production for addToProducePerHour method
        Field previousField = villageEntity.getFields().get(fieldTaskModel.getPosition());
        int differenceProduction = upgradedField.getProductivity() - previousField.getProductivity();
        // set new field to the village
        villageEntity.getFields().set(upgradedField.getPosition(), upgradedField);
        // add to ProducePerHour
        addToProducePerHour(villageEntity, upgradedField.getFieldType(), differenceProduction);
    }*/

    /*private void addToProducePerHour(VillageEntity villageEntity, FieldType fieldType, Integer amount){
        Map<FieldType, Integer> previous = villageEntity.getProducePerHour().getGoods();
        // add or subtract amount
        villageEntity.getProducePerHour().getGoods().put(fieldType, previous.get(fieldType) + amount);
    }*/
}

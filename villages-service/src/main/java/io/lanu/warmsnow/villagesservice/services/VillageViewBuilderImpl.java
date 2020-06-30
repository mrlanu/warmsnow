package io.lanu.warmsnow.villagesservice.services;

import io.lanu.warmsnow.common_models.FieldType;
import io.lanu.warmsnow.common_models.models.Field;
import io.lanu.warmsnow.common_models.models.Warehouse;
import io.lanu.warmsnow.templates.templates_client.dto.VillageDto;
import io.lanu.warmsnow.villagesservice.clients.ArmiesServiceFeignClient;
import io.lanu.warmsnow.villagesservice.clients.ConstructionsServiceFeignClient;
import io.lanu.warmsnow.villagesservice.entities.VillageEntity;
import io.lanu.warmsnow.villagesservice.models.FieldTask;
import io.lanu.warmsnow.villagesservice.models.TaskExecution;
import io.lanu.warmsnow.villagesservice.models.TroopTask;
import io.lanu.warmsnow.villagesservice.repositories.VillageRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class VillageViewBuilderImpl implements VillageViewBuilder{

    private final VillageRepository villageRepository;
    private final ConstructionsServiceFeignClient constructionsClient;
    private final ArmiesServiceFeignClient armiesClient;

    private final ModelMapper MAPPER = new ModelMapper();

    public VillageViewBuilderImpl(VillageRepository villageRepository, ConstructionsServiceFeignClient constructionsClient,
                                  ArmiesServiceFeignClient armiesClient) {
        this.villageRepository = villageRepository;
        this.constructionsClient = constructionsClient;
        this.armiesClient = armiesClient;
    }

    @Override
    public VillageDto build(String villageId) {
        List<TaskExecution> taskExecutions = new ArrayList<>();
        // fetch the Village
        VillageEntity villageEntity = villageRepository.findById(villageId).orElseThrow();
        // fetch construction tasks
        List<FieldTask> fieldTasks = constructionsClient.getTasksByVillageId(villageId);

        // fetch army tasks
        List<TroopTask> armyTasks = armiesClient.getTasksByVillageId(villageId);

        // if the task hasn't payed, subtract goods from the Warehouse
        fieldTasks.stream()
                .filter(fieldTask -> !fieldTask.isPaid())
                .forEach(fieldTask -> subtractResourcesFromWarehouse(villageEntity, fieldTask.getFieldOld().getResourcesToNextLevel()));

        // check whether is Field under upgrade if so change its status
        checkFieldUnderUpgrade(villageEntity, fieldTasks);

        // combine all tasks together
        taskExecutions.addAll(fieldTasks);
        taskExecutions.addAll(armyTasks);

        calculateProducedGoods(villageEntity, taskExecutions);
        checkFieldsUpgradable(villageEntity);

        // save VillageEntity after all counting
        villageRepository.save(villageEntity);
        return MAPPER.map(villageEntity, VillageDto.class);
    }

    private void subtractResourcesFromWarehouse(VillageEntity villageEntity, Map<FieldType, BigDecimal> resourcesToNextLevel) {
        Warehouse warehouse = villageEntity.getWarehouse();
        warehouse.getGoods()
                .forEach((k, v) -> warehouse.getGoods().put(k, warehouse.getGoods().get(k).subtract(resourcesToNextLevel.get(k))));
    }

    private void checkFieldUnderUpgrade(VillageEntity villageEntity, List<FieldTask> fieldTasks) {
        fieldTasks.forEach(fieldTask -> {
            Field f = villageEntity.getFields().get(fieldTask.getFieldNew().getPosition());
            f.setUnderUpgrade(true);
        });
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
}
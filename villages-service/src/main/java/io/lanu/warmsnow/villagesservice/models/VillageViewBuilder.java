package io.lanu.warmsnow.villagesservice.models;

import io.lanu.warmsnow.common_models.FieldType;
import io.lanu.warmsnow.common_models.models.Field;
import io.lanu.warmsnow.common_models.models.ProducePerHour;
import io.lanu.warmsnow.common_models.models.TaskModel;
import io.lanu.warmsnow.templates.templates_client.dto.FieldDto;
import io.lanu.warmsnow.templates.templates_client.dto.VillageDto;
import io.lanu.warmsnow.villagesservice.clients.TemplatesServiceFeignClient;
import io.lanu.warmsnow.villagesservice.entities.VillageEntity;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class VillageViewBuilder implements Builder {

    private VillageEntity villageEntity;

    private final TemplatesServiceFeignClient templatesService;

    private final ModelMapper MAPPER = new ModelMapper();


    public VillageViewBuilder(TemplatesServiceFeignClient templatesService) {
        this.templatesService = templatesService;
    }

    @Override
    public void reset(VillageEntity villageEntity) {
        this.villageEntity = villageEntity;
    }

    public void checkFieldsUpgradable(){
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


    @Override
    public void calculateProducedGoods(){
        // filter all tasks that the village has with time before now
        List<TaskModel> taskModels = villageEntity.getTasks()
                .stream()
                .filter(taskModel -> taskModel.getCompletedTime().isBefore(LocalDateTime.now()))
                .sorted(Comparator.comparing(TaskModel::getCompletedTime))
                .collect(Collectors.toList());

        LocalDateTime modified = villageEntity.getModified();

        // if the village doesnt have any tasks
        if (taskModels.size() == 0) {
            calculate(villageEntity.getModified(), LocalDateTime.now());
        } else { // if the village does any tasks lastModified should be changed every task's completed time
            // calculate between each task which should be completed before now
            for (TaskModel task : taskModels) {
                calculate(modified, task.getCompletedTime());
                modified = task.getCompletedTime();
                upgradeField(task);
                villageEntity.getTasks().remove(task);
            }
            // last calculate between last task and now
            calculate(modified, LocalDateTime.now());
        }
    }

    private void recalculateProducePerHour(){
        Map<FieldType, Integer> productionPerHour = villageEntity.getFields()
                .stream()
                .collect(Collectors.groupingBy(Field::getFieldType,
                        Collectors.summingInt(Field::getProductivity)));
        villageEntity.getProducePerHour().setGoods(productionPerHour);
    }

    private void calculate(LocalDateTime lastModified, LocalDateTime untilTime){
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

    private void upgradeField(TaskModel taskModel) {
        // get new field from Templates service
        FieldDto upgradedFieldTemplate = templatesService
                .getFieldByLevelAndType(taskModel.getLevel(), taskModel.getFieldType());
        upgradedFieldTemplate.setPosition(taskModel.getPosition());
        Field upgradedField = MAPPER.map(upgradedFieldTemplate, Field.class);
        // set new field to the village
        villageEntity.getFields().set(upgradedField.getPosition(), upgradedField);
        recalculateProducePerHour();
    }

    @Override
    public void recalculateTasksTimeLeft(){
        villageEntity.getTasks().forEach(taskModel -> {
            taskModel
                .setTimeLeft(ChronoUnit.SECONDS.between(LocalDateTime.now(), taskModel.getCompletedTime()));
        });
    }

    public VillageDto getProduct(){
        return MAPPER.map(villageEntity, VillageDto.class);
    }
}

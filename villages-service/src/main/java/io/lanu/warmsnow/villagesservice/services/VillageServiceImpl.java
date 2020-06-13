package io.lanu.warmsnow.villagesservice.services;

import io.lanu.warmsnow.common_models.FieldType;
import io.lanu.warmsnow.common_models.models.Warehouse;
import io.lanu.warmsnow.common_models.requests.FieldUpgradeRequest;
import io.lanu.warmsnow.templates.templates_client.dto.FieldDto;
import io.lanu.warmsnow.templates.templates_client.dto.VillageDto;
import io.lanu.warmsnow.common_models.models.Field;
import io.lanu.warmsnow.villagesservice.clients.ScheduleServiceFeignClient;
import io.lanu.warmsnow.villagesservice.clients.TemplatesServiceFeignClient;
import io.lanu.warmsnow.villagesservice.entities.VillageEntity;
import io.lanu.warmsnow.common_models.requests.NewVillageRequest;
import io.lanu.warmsnow.villagesservice.repositories.VillageRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class VillageServiceImpl implements VillageService {

    private VillageRepository villageRepository;
    private TemplatesServiceFeignClient templatesFeignClient;
    private final ModelMapper MAPPER = new ModelMapper();
    private final ScheduleServiceFeignClient scheduleClient;

    public VillageServiceImpl(VillageRepository villageRepository,
                              TemplatesServiceFeignClient templatesFeignClient, ScheduleServiceFeignClient scheduleClient) {
        this.villageRepository = villageRepository;
        this.templatesFeignClient = templatesFeignClient;
        this.scheduleClient = scheduleClient;
    }

    @Override
    public VillageEntity save(VillageEntity villageEntity) {
        return villageRepository.save(villageEntity);
    }

    @Override
    public VillageDto findById(String id) {
        VillageEntity villageEntity = villageRepository.findById(id).orElseThrow();
        checkFieldsUpgradable(villageEntity);
        villageEntity = calculateProducedGoods(villageEntity);
        return MAPPER.map(villageEntity, VillageDto.class);
    }

    @Override
    public List<VillageEntity> findAll() {
        return villageRepository.findAll();
    }

    @Override
    public VillageDto createVillage(NewVillageRequest newVillageRequest) {
        // get the village template from Template service
        VillageDto villageTemplate = templatesFeignClient.getVillageByType(newVillageRequest.getVillageType());
        // map data from template to entity
        VillageEntity villageEntity = MAPPER.map(villageTemplate, VillageEntity.class);
        //  set some properties
        villageEntity.setAccountId(newVillageRequest.getAccountId());
        villageEntity.setX(newVillageRequest.getX());
        villageEntity.setY(newVillageRequest.getY());
        // check weather could be fields upgrade or not
        checkFieldsUpgradable(villageEntity);
        // save the village entity to DB
        VillageEntity newVillage = villageRepository.save(villageEntity);
        log.info("New village has been created");
        // return stored village mapped to DTO
        return MAPPER.map(newVillage, VillageDto.class);
    }

    private void checkFieldsUpgradable(VillageEntity villageEntity){
        villageEntity.getFields()
                .forEach(field -> {
                    if (compareResources(field.getResourcesToNextLevel(), villageEntity.getWarehouse().getGoods())){
                        field.setAbleToUpgrade(true);
                    }
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
    public void scheduleFieldUpgrade(FieldUpgradeRequest request) {
        // get the village for upgrade
        VillageEntity villageEntity = villageRepository.findById(request.getVillageId()).orElseThrow();
        // get the field for upgrade in that village
        Field field = villageEntity.getFields().get(request.getField().getPosition());
        field.setUnderUpgrade(true);
        field.setTimeUpgradeComplete(LocalDateTime.now().plus(field.getTimeToNextLevel(), ChronoUnit.MILLIS));
        // subtract needed resources for upgrade the Field
        Warehouse warehouse = villageEntity.getWarehouse();
        subtractResourcesFromWarehouse(warehouse, field.getResourcesToNextLevel());
        //schedule the task
        ResponseEntity<String> response = scheduleClient.requestFieldUpgrade(request);
        log.info("Field upgrade scheduled - " + response.getBody());
        // save the village to DB
        villageRepository.save(villageEntity);
    }

    private void subtractResourcesFromWarehouse(Warehouse warehouse, Map<FieldType, BigDecimal> resourcesToNextLevel) {
        warehouse.getGoods()
                .forEach((k, v) -> warehouse.getGoods().put(k, warehouse.getGoods().get(k).subtract(resourcesToNextLevel.get(k))));
    }

    @Override
    public VillageDto upgradeField(FieldUpgradeRequest request) {
        // get the village for upgrade
        VillageEntity villageEntity = villageRepository.findById(request.getVillageId()).orElseThrow();
        // get the field for upgrade in that village
        Field field = villageEntity.getFields().get(request.getField().getPosition());
        // get new field from Templates service
        FieldDto upgradedFieldTemplate = templatesFeignClient
                .getFieldByLevelAndType(field.getLevel() + 1, field.getFieldType());
        upgradedFieldTemplate.setPosition(field.getPosition());
        Field upgradedField = MAPPER.map(upgradedFieldTemplate, Field.class);
        // set new field to the village
        villageEntity.getFields().set(upgradedField.getPosition(), upgradedField);
        // save upgraded village
        villageEntity = save(villageEntity);
        // check fields available for upgrade
        checkFieldsUpgradable(villageEntity);
        // return mapped village to DTO
        return MAPPER.map(villageEntity, VillageDto.class);
    }

    private VillageEntity calculateProducedGoods(VillageEntity villageEntity){

        Long durationFromLastModified = Duration
                .between(villageEntity.getModified(), LocalDateTime.now()).toMillis();

        Map<FieldType, Double> productionPerHour = villageEntity.getFields()
                .stream()
                .collect(Collectors.groupingBy(Field::getFieldType,
                        Collectors.summingDouble(buildingView -> buildingView.getProductivity().doubleValue())));

        MathContext mc = new MathContext(3);
        BigDecimal wood =
                new BigDecimal((durationFromLastModified * productionPerHour.get(FieldType.WOOD)) / 3600000L, mc);
        BigDecimal clay =
                new BigDecimal((durationFromLastModified * productionPerHour.get(FieldType.CLAY)) / 3600000L, mc);
        BigDecimal iron =
                new BigDecimal((durationFromLastModified * productionPerHour.get(FieldType.IRON)) / 3600000L, mc);
        BigDecimal crop =
                new BigDecimal((durationFromLastModified * productionPerHour.get(FieldType.CROP)) / 3600000L, mc);

        Map<FieldType, BigDecimal> goods = villageEntity.getWarehouse().getGoods();
        goods.put(FieldType.WOOD, goods.get(FieldType.WOOD).add(wood));
        goods.put(FieldType.CLAY, goods.get(FieldType.CLAY).add(clay));
        goods.put(FieldType.IRON, goods.get(FieldType.IRON).add(iron));
        goods.put(FieldType.CROP, goods.get(FieldType.CROP).add(crop));

        log.info("Produced resources added to the Warehouse.");

        return save(villageEntity);
    }
}

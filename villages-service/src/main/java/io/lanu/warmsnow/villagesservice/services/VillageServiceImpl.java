package io.lanu.warmsnow.villagesservice.services;

import io.lanu.warmsnow.common_models.FieldType;
import io.lanu.warmsnow.common_models.models.Field;
import io.lanu.warmsnow.common_models.models.TaskType;
import io.lanu.warmsnow.common_models.models.Warehouse;
import io.lanu.warmsnow.common_models.requests.FieldUpgradeRequest;
import io.lanu.warmsnow.common_models.requests.NewVillageRequest;
import io.lanu.warmsnow.templates.templates_client.dto.FieldDto;
import io.lanu.warmsnow.templates.templates_client.dto.VillageDto;
import io.lanu.warmsnow.villagesservice.clients.TemplatesServiceFeignClient;
import io.lanu.warmsnow.villagesservice.entities.VillageEntity;
import io.lanu.warmsnow.common_models.models.FieldTaskModel;
import io.lanu.warmsnow.villagesservice.models.VillageViewBuilder;
import io.lanu.warmsnow.villagesservice.models.VillageViewDirector;
import io.lanu.warmsnow.villagesservice.repositories.VillageRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
public class VillageServiceImpl implements VillageService {

    private VillageRepository villageRepository;
    private TemplatesServiceFeignClient templatesFeignClient;
    private final ModelMapper MAPPER = new ModelMapper();
    private final VillageViewBuilder builder;

    public VillageServiceImpl(VillageRepository villageRepository, TemplatesServiceFeignClient templatesFeignClient,
                              VillageViewBuilder builder) {
        this.villageRepository = villageRepository;
        this.templatesFeignClient = templatesFeignClient;
        this.builder = builder;
    }

    @Override
    public VillageEntity save(VillageEntity villageEntity) {
        return villageRepository.save(villageEntity);
    }

    @Override
    public VillageDto getVillageById(String id) {
        VillageEntity villageEntity = villageRepository.findById(id).orElseThrow();
        // implementation of builder template for construct VillageView
        builder.reset(villageEntity);
        VillageViewDirector director = new VillageViewDirector(builder);
        director.constructVillageView();
        // save VillageEntity after all counting in Builder
        save(villageEntity);
        return builder.getProduct();
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

    private void subtractResourcesFromWarehouse(Warehouse warehouse, Map<FieldType, BigDecimal> resourcesToNextLevel) {
        warehouse.getGoods()
                .forEach((k, v) -> warehouse.getGoods().put(k, warehouse.getGoods().get(k).subtract(resourcesToNextLevel.get(k))));
    }

    @Override
    public void upgradeField(FieldUpgradeRequest request) {
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
        save(villageEntity);
    }
}

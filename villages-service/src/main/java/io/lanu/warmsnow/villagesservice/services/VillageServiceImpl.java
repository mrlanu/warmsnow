package io.lanu.warmsnow.villagesservice.services;

import io.lanu.warmsnow.common_models.models.buildings.BuildingBase;
import io.lanu.warmsnow.common_models.requests.NewVillageRequest;
import io.lanu.warmsnow.templates.templates_client.dto.VillageDto;
import io.lanu.warmsnow.villagesservice.clients.TemplatesServiceFeignClient;
import io.lanu.warmsnow.villagesservice.entities.VillageEntity;
import io.lanu.warmsnow.villagesservice.repositories.VillageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class VillageServiceImpl implements VillageService {

    private VillageRepository villageRepository;
    private TemplatesServiceFeignClient templatesFeignClient;
    private final VillageViewBuilder villageViewBuilder;

    public VillageServiceImpl(VillageRepository villageRepository, TemplatesServiceFeignClient templatesFeignClient,
                              VillageViewBuilder villageViewBuilder) {
        this.villageRepository = villageRepository;
        this.templatesFeignClient = templatesFeignClient;
        this.villageViewBuilder = villageViewBuilder;
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
    public VillageDto getVillageById(String villageId) {
       return villageViewBuilder.build(villageId);
    }

    @Override
    public List<BuildingBase> getAvailableBuildings(String villageId) {
        List<BuildingBase> buildings = templatesFeignClient.getAllBuildings();
        VillageEntity villageEntity = villageRepository.findById(villageId).get();
        return buildings
                .stream()
                .peek(b -> b.couldBeBuild(villageEntity.getBuildings(), villageEntity.getWarehouse(), b.getResourcesToNextLevel()))
                .collect(Collectors.toList());
    }
}

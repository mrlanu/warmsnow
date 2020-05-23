package io.lanu.warmsnow.villagesservice.services;

import io.lanu.warmsnow.buildings_service.buildings_client.dto.BuildingDto;
import io.lanu.warmsnow.villagesservice.clients.BuildingsServiceFeignClient;
import io.lanu.warmsnow.villagesservice.entities.BuildingEntity;
import io.lanu.warmsnow.villagesservice.entities.VillageEntity;
import io.lanu.warmsnow.villagesservice.repositories.VillageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class VillageServiceImpl implements VillageService {

    private VillageRepository villageRepository;
    private BuildingsServiceFeignClient buildingsClient;

    public VillageServiceImpl(VillageRepository villageRepository,
                              BuildingsServiceFeignClient buildingsClient) {
        this.villageRepository = villageRepository;
        this.buildingsClient = buildingsClient;
    }

    @Override
    public VillageEntity save(VillageEntity villageEntity) {
        return villageRepository.save(villageEntity);
    }

    @Override
    public VillageEntity findById(String id) {
        return villageRepository.findById(id).get();
    }

    @Override
    public List<VillageEntity> findAll() {
        return villageRepository.findAll();
    }

    @Override
    public VillageEntity createVillage(String accountId) {
        log.info("New village has been created");
        return villageRepository.save(new VillageEntity(accountId));
    }

    @Override
    public VillageEntity createBuilding(BuildingEntity buildingEntity) {
        VillageEntity village = findById(buildingEntity.getVillageId());
        village.addBuilding(buildingEntity);
        return save(village);
    }

    @Override
    public List<BuildingDto> getAvailableBuildings() {
        return buildingsClient.getAvailableBuildings();
    }

    @Override
    public BuildingDto getBuildingById(String buildingId) {
        return buildingsClient.getBuilding(buildingId);
    }
}

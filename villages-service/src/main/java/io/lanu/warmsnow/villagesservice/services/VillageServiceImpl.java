package io.lanu.warmsnow.villagesservice.services;

import io.lanu.warmsnow.buildings_service.buildings_client.dto.BuildingDto;
import io.lanu.warmsnow.buildings_service.buildings_client.dto.WarehouseDto;
import io.lanu.warmsnow.villagesservice.clients.BuildingsServiceFeignClient;
import io.lanu.warmsnow.villagesservice.entities.VillageEntity;
import io.lanu.warmsnow.villagesservice.models.BuildingModel;
import io.lanu.warmsnow.villagesservice.repositories.VillageRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class VillageServiceImpl implements VillageService {

    private VillageRepository villageRepository;
    private BuildingsServiceFeignClient buildingsClient;
    private final ModelMapper MAPPER = new ModelMapper();

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
    public VillageEntity addNewBuilding(BuildingDto buildingDto) {
        VillageEntity village = findById(buildingDto.getVillageId());
        BuildingModel buildingModel = MAPPER.map(buildingDto, BuildingModel.class);
        village.addBuilding(buildingModel);
        return save(village);
    }

    @Override
    public List<BuildingDto> getAvailableBuildings(String villageId) {
        VillageEntity villageEntity = findById(villageId);
        WarehouseDto warehouseDto = MAPPER.map(villageEntity.getWarehouse(), WarehouseDto.class);
        return buildingsClient.getAvailableBuildings(warehouseDto);
    }

    @Override
    public BuildingDto getBuildingById(String buildingId) {
        return buildingsClient.getBuilding(buildingId);
    }
}

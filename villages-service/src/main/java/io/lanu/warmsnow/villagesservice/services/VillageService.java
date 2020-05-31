package io.lanu.warmsnow.villagesservice.services;

import io.lanu.warmsnow.templates.templates_client.dto.BuildingDto;
import io.lanu.warmsnow.villagesservice.entities.VillageEntity;

import java.util.List;

public interface VillageService {
    VillageEntity createVillage(String accountId);
    List<VillageEntity> findAll();
    VillageEntity findById(String id);
    VillageEntity save(VillageEntity villageEntity);
    VillageEntity addNewBuilding(BuildingDto buildingDto);
    List<BuildingDto> getAvailableBuildings(String villageId);
    BuildingDto getBuildingById(String buildingId);
}

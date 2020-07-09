package io.lanu.warmsnow.villagesservice.services;

import io.lanu.warmsnow.common_models.models.buildings.BuildingBase;
import io.lanu.warmsnow.common_models.requests.NewVillageRequest;
import io.lanu.warmsnow.templates.templates_client.dto.VillageDto;
import io.lanu.warmsnow.villagesservice.entities.VillageEntity;

import java.util.List;

public interface VillageService {
    VillageEntity createVillage(NewVillageRequest newVillageRequest);
    VillageDto getVillageById(String id);

    List<BuildingBase> getAvailableBuildings(String villageId);
}

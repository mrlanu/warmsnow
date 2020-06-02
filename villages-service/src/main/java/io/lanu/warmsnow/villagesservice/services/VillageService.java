package io.lanu.warmsnow.villagesservice.services;

import io.lanu.warmsnow.templates.templates_client.dto.VillageDto;
import io.lanu.warmsnow.villagesservice.entities.VillageEntity;
import io.lanu.warmsnow.villagesservice.models.NewVillageRequest;

import java.util.List;

public interface VillageService {
    VillageDto createVillage(NewVillageRequest newVillageRequest);
    List<VillageEntity> findAll();
    VillageDto findById(String id);
    VillageEntity save(VillageEntity villageEntity);

    VillageDto upgradeField(String villageId, int fieldPosition);
}

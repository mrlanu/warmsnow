package io.lanu.warmsnow.villagesservice.services;

import io.lanu.warmsnow.common_models.requests.FieldUpgradeRequest;
import io.lanu.warmsnow.templates.templates_client.dto.VillageDto;
import io.lanu.warmsnow.villagesservice.entities.VillageEntity;
import io.lanu.warmsnow.common_models.requests.NewVillageRequest;

import java.util.List;

public interface VillageService {
    VillageDto createVillage(NewVillageRequest newVillageRequest);
    List<VillageEntity> findAll();
    VillageDto findById(String id);
    VillageEntity save(VillageEntity villageEntity);

    void scheduleFieldUpgrade(FieldUpgradeRequest request);
    VillageDto upgradeField(FieldUpgradeRequest request);
}

package io.lanu.warmsnow.villagesservice.services;

import io.lanu.warmsnow.common_models.requests.FieldUpgradeRequest;
import io.lanu.warmsnow.common_models.requests.NewVillageRequest;
import io.lanu.warmsnow.templates.templates_client.dto.VillageDto;
import io.lanu.warmsnow.villagesservice.entities.VillageEntity;

public interface VillageService {
    VillageEntity createVillage(NewVillageRequest newVillageRequest);
    VillageDto getVillageById(String id);

    void scheduleFieldUpgrade(FieldUpgradeRequest request);
}

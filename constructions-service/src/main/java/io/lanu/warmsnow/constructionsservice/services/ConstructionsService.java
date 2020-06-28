package io.lanu.warmsnow.constructionsservice.services;

import io.lanu.warmsnow.common_models.dto.FieldTaskDto;
import io.lanu.warmsnow.common_models.requests.FieldUpgradeRequest;

import java.util.List;

public interface ConstructionsService {
    void scheduleFieldUpgrade(FieldUpgradeRequest request);
    List<FieldTaskDto> getAllTasksByVillageId(String villageId);
}

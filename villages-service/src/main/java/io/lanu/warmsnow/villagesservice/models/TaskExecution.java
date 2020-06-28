package io.lanu.warmsnow.villagesservice.models;

import io.lanu.warmsnow.villagesservice.entities.VillageEntity;

import java.time.LocalDateTime;

public interface TaskExecution {
    void executeTask(VillageEntity villageEntity);
    LocalDateTime getExecutionTime();
}

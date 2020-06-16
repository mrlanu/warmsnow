package io.lanu.warmsnow.villagesservice.models;

import io.lanu.warmsnow.villagesservice.entities.VillageEntity;

public interface Builder {
    void reset(VillageEntity villageEntity);
    void checkFieldsUpgradable();
    void calculateProducedGoods();
    void recalculateTasksTimeLeft();
}

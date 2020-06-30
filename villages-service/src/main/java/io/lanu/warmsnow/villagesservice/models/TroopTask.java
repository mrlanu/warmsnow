package io.lanu.warmsnow.villagesservice.models;

import io.lanu.warmsnow.common_models.FieldType;
import io.lanu.warmsnow.common_models.UnitType;
import io.lanu.warmsnow.villagesservice.entities.VillageEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@NoArgsConstructor
public class TroopTask implements TaskExecution {

    private String villageId;
    private UnitType unitType;
    private int eatHour;
    private LocalDateTime executionTime;

    @Override
    public void executeTask(VillageEntity villageEntity) {
        Map<UnitType, Integer> army = villageEntity.getArmy().getHomeLegion();
        army.put(unitType, army.getOrDefault(unitType, 0) + 1);
        villageEntity.addToProducePerHour(FieldType.CROP, -eatHour);
    }
}

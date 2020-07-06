package io.lanu.warmsnow.villagesservice.models.tasks;

import io.lanu.warmsnow.common_models.FieldType;
import io.lanu.warmsnow.common_models.UnitType;
import io.lanu.warmsnow.villagesservice.entities.VillageEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class TroopTask extends BaseTask {

    private String villageId;
    private UnitType unitType;
    private int eatHour;

    public TroopTask(LocalDateTime executionTime, UnitType unitType, int eatHour) {
        super(executionTime);
        this.unitType = unitType;
        this.eatHour = eatHour;
    }

    @Override
    public void performActions(VillageEntity villageEntity) {
        Map<UnitType, Integer> army = villageEntity.getArmy().getHomeLegion();
        army.put(unitType, army.getOrDefault(unitType, 0) + 1);
        villageEntity.getProducePerHour().addGood(FieldType.CROP, -eatHour);
    }
}

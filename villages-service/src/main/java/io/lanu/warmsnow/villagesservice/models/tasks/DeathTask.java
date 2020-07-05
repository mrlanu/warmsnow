package io.lanu.warmsnow.villagesservice.models.tasks;

import io.lanu.warmsnow.common_models.FieldType;
import io.lanu.warmsnow.common_models.UnitType;
import io.lanu.warmsnow.villagesservice.entities.VillageEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class DeathTask extends BaseTask {

    public DeathTask(LocalDateTime executionTime) {
        super(executionTime);
    }

    @Override
    public void performActions(VillageEntity villageEntity) {
        Map<UnitType, Integer> army = villageEntity.getArmy().getHomeLegion();
        army.put(UnitType.LEGIONNAIRE, army.getOrDefault(UnitType.LEGIONNAIRE, 0) - 1);
        villageEntity.getWarehouse().addGood(FieldType.CROP, BigDecimal.valueOf(4));
        villageEntity.addToProducePerHour(FieldType.CROP, 1);
    }
}

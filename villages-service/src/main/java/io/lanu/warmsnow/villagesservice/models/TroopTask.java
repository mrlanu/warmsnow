package io.lanu.warmsnow.villagesservice.models;

import io.lanu.warmsnow.common_models.UnitType;
import io.lanu.warmsnow.common_models.models.Unit;
import io.lanu.warmsnow.villagesservice.entities.VillageEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class TroopTask implements TaskExecution {

    private String taskId;
    private String villageId;
    private UnitType unitType;
    private LocalDateTime executionTime;

    @Override
    public void executeTask(VillageEntity villageEntity) {

    }

    @Override
    public LocalDateTime getExecutionTime() {
        return null;
    }
}

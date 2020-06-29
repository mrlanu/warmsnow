package io.lanu.warmsnow.common_models.dto;

import io.lanu.warmsnow.common_models.UnitType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class TroopTaskDto {
    private String taskId;
    private String villageId;
    private UnitType unitType;
    private LocalDateTime executionTime;

    public TroopTaskDto(String villageId, UnitType unitType, LocalDateTime executionTime) {
        this.villageId = villageId;
        this.unitType = getUnitType();
        this.executionTime = executionTime;
    }
}

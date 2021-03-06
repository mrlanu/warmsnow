package io.lanu.warmsnow.common_models.dto;

import io.lanu.warmsnow.common_models.UnitType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class TroopTaskDto {
    private String villageId;
    private UnitType unitType;
    private int eatHour;
    private LocalDateTime executionTime;

    public TroopTaskDto(String villageId, UnitType unitType, int eatHour, LocalDateTime executionTime) {
        this.villageId = villageId;
        this.unitType = unitType;
        this.eatHour = eatHour;
        this.executionTime = executionTime;
    }
}

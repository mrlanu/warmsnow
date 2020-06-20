package io.lanu.warmsnow.armiesservice.models;

import io.lanu.warmsnow.common_models.UnitType;
import lombok.Data;

@Data
public class ArmyOrderRequest {
    private String villageId;
    private UnitType unitType;
    private Integer buildingLevel; // barrack or stable etc.
    private Integer amount;
}

package io.lanu.warmsnow.armiesservice.models;

import io.lanu.warmsnow.common_models.NationsType;
import lombok.Data;

@Data
public class ArmyOrderRequest {
    private String villageId;
    private NationsType nationsType;
    private String troopId;
    private Integer buildingLevel; // barrack or stable etc.
    private Integer amount;
}

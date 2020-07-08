package io.lanu.warmsnow.common_models.requests;

import io.lanu.warmsnow.common_models.models.buildings.BuildingBase;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuildingUpgradeRequest {
    private String villageId;
    private BuildingBase building;
}

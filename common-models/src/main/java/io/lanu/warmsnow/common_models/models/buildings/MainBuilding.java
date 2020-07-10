package io.lanu.warmsnow.common_models.models.buildings;

import io.lanu.warmsnow.common_models.FieldType;
import io.lanu.warmsnow.common_models.models.BuildingType;
import io.lanu.warmsnow.common_models.models.buildings.requirements.MainBuildingReq;
import io.lanu.warmsnow.common_models.models.buildings.requirements.RequiredBase;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Map;

@Getter
@Setter
public class MainBuilding extends BuildingBase {
    private Integer timeReduction;

    public MainBuilding(int level, int position, Map<FieldType, BigDecimal> resourcesToNextLevel,
                        long timeToNextLevel, Integer timeReduction) {
        super(BuildingType.MAIN, level, position, resourcesToNextLevel, timeToNextLevel);
        this.timeReduction = timeReduction;
    }

    @Override
    public RequiredBase createTester() {
        return new MainBuildingReq();
    }
}

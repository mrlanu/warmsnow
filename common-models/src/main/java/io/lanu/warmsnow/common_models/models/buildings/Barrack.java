package io.lanu.warmsnow.common_models.models.buildings;

import io.lanu.warmsnow.common_models.FieldType;
import io.lanu.warmsnow.common_models.models.BuildingType;
import io.lanu.warmsnow.common_models.models.buildings.requirements.BarrackReq;
import io.lanu.warmsnow.common_models.models.buildings.requirements.RequiredBase;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Map;

@Getter
@Setter
public class Barrack extends BuildingBase {
    private Integer timeReduction;

    public Barrack(int level, int position, Map<FieldType, BigDecimal> resourcesToNextLevel,
                   long timeToNextLevel, Integer timeReduction) {
        super(BuildingType.BARRACK, level, position, resourcesToNextLevel, timeToNextLevel);
        this.timeReduction = timeReduction;
    }

    @Override
    public RequiredBase createTester() {
        return new BarrackReq();
    }
}

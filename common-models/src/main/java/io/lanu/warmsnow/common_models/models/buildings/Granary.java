package io.lanu.warmsnow.common_models.models.buildings;

import io.lanu.warmsnow.common_models.FieldType;
import io.lanu.warmsnow.common_models.models.BuildingType;
import io.lanu.warmsnow.common_models.models.buildings.requirements.GranaryReq;
import io.lanu.warmsnow.common_models.models.buildings.requirements.RequiredBase;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Map;

@Getter
@Setter
public class Granary extends BuildingBase {
    private Integer capacity;

    public Granary(int level, int position, Map<FieldType, BigDecimal> resourcesToNextLevel,
                   long timeToNextLevel, Integer capacity) {
        super(BuildingType.GRANARY, level, position, resourcesToNextLevel, timeToNextLevel);
        this.capacity = capacity;
    }

    @Override
    public RequiredBase createTester() {
        return new GranaryReq();
    }
}

package io.lanu.warmsnow.common_models.models.buildings;

import io.lanu.warmsnow.common_models.FieldType;
import io.lanu.warmsnow.common_models.models.BuildingType;
import io.lanu.warmsnow.common_models.models.buildings.requirements.RequiredBase;
import io.lanu.warmsnow.common_models.models.buildings.requirements.WarehouseReq;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Map;

@Getter
@Setter
public class WarehouseBuilding extends BuildingBase {
    private Integer capacity;

    public WarehouseBuilding(int level, int position, Map<FieldType, BigDecimal> resourcesToNextLevel,
                             long timeToNextLevel, Integer capacity) {
        super(BuildingType.WAREHOUSE, level, position, resourcesToNextLevel, timeToNextLevel);
        this.capacity = capacity;
    }

    @Override
    public RequiredBase createTester() {
        return new WarehouseReq();
    }
}

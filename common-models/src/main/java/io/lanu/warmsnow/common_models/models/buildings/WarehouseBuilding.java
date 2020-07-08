package io.lanu.warmsnow.common_models.models.buildings;

import io.lanu.warmsnow.common_models.FieldType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
public class WarehouseBuilding extends BuildingBase {
    private Integer capacity;

    public WarehouseBuilding(int level, int position, Map<FieldType, Integer> resourcesToNextLevel,
                             long timeToNextLevel, Integer capacity) {
        super("warehouse", level, position, resourcesToNextLevel, timeToNextLevel);
        this.capacity = capacity;
    }

    @Override
    public String createTester() {
        return "Warehouse testing";
    }
}

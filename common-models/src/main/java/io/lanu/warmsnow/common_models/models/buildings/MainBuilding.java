package io.lanu.warmsnow.common_models.models.buildings;

import io.lanu.warmsnow.common_models.FieldType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
public class MainBuilding extends BuildingBase {
    private Integer timeReduction;

    public MainBuilding(int level, int position, Map<FieldType, Integer> resourcesToNextLevel,
                        long timeToNextLevel, Integer timeReduction) {
        super("main-building", level, position, resourcesToNextLevel, timeToNextLevel);
        this.timeReduction = timeReduction;
    }

    @Override
    public String createTester() {
        return "Main testing";
    }
}

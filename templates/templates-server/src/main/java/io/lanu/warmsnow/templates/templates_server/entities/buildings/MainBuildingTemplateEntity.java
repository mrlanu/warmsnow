package io.lanu.warmsnow.templates.templates_server.entities.buildings;

import io.lanu.warmsnow.common_models.FieldType;
import io.lanu.warmsnow.common_models.models.BuildingType;

import java.math.BigDecimal;
import java.util.Map;

public class MainBuildingTemplateEntity extends BuildingBaseTemplateEntity{
    private Integer timeReduction;

    public MainBuildingTemplateEntity(int level, int position, Map<FieldType, BigDecimal> resourcesToNextLevel,
                        long timeToNextLevel, Integer timeReduction) {
        super(BuildingType.MAIN, level, position, resourcesToNextLevel, timeToNextLevel);
        this.timeReduction = timeReduction;
    }

}

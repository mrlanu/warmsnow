package io.lanu.warmsnow.templates.templates_server.entities.buildings;

import io.lanu.warmsnow.common_models.FieldType;

import java.util.Map;

public class MainBuildingTemplateEntity extends BuildingBaseTemplateEntity{
    private Integer timeReduction;

    public MainBuildingTemplateEntity(int level, int position, Map<FieldType, Integer> resourcesToNextLevel,
                        long timeToNextLevel, Integer timeReduction) {
        super("main-building", level, position, resourcesToNextLevel, timeToNextLevel);
        this.timeReduction = timeReduction;
    }

}

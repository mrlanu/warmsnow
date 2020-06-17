package io.lanu.warmsnow.templates.templates_client.dto;

import io.lanu.warmsnow.common_models.FieldType;
import io.lanu.warmsnow.common_models.NationsType;
import io.lanu.warmsnow.common_models.UnitType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnitDto {
    private String unitId;
    private UnitType unitType;
    private NationsType nationsType;
    private Map<FieldType, Integer> resources;
    private Integer offense;
    private Integer defenseInf;
    private Integer defenseCav;
    private Integer speed;
    private Integer opacity;
    private Integer eatHour;
    private long baseProductionTime; // seconds
}

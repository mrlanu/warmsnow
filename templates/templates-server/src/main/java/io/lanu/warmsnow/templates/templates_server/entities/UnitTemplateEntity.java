package io.lanu.warmsnow.templates.templates_server.entities;

import io.lanu.warmsnow.common_models.FieldType;
import io.lanu.warmsnow.common_models.NationsType;
import io.lanu.warmsnow.common_models.UnitType;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Data
@Document("units-templates")
@TypeAlias("units-templates")
@NoArgsConstructor
public class UnitTemplateEntity {

    public UnitTemplateEntity(UnitType unitType, NationsType nationsType, Map<FieldType, Integer> resources,
                              Integer offense, Integer defenseInf, Integer defenseCav, Integer speed, Integer opacity,
                              Integer eatHour, long baseProductionTime) {
        this.unitType = unitType;
        this.nationsType = nationsType;
        this.resources = resources;
        this.offense = offense;
        this.defenseInf = defenseInf;
        this.defenseCav = defenseCav;
        this.speed = speed;
        this.opacity = opacity;
        this.eatHour = eatHour;
        this.baseProductionTime = baseProductionTime;
    }

    @Id
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

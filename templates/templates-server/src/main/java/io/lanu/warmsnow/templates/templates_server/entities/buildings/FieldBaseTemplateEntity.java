package io.lanu.warmsnow.templates.templates_server.entities.buildings;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class FieldBaseTemplateEntity extends BuildingBaseTemplateEntity {

    /*private String type;
    private Integer production;

    public FieldBase(Integer wood, Integer clay, Integer iron, Integer crop,
                     Long duration, String type, Integer production) {
        super("field", wood, clay, iron, crop, duration);
        this.type = type;
        this.production = production;
    }*/

}

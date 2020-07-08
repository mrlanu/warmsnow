package io.lanu.warmsnow.common_models.models.buildings;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class FieldBase extends BuildingBase {

    /*private String type;
    private Integer production;

    public FieldBase(Integer wood, Integer clay, Integer iron, Integer crop,
                     Long duration, String type, Integer production) {
        super("field", wood, clay, iron, crop, duration);
        this.type = type;
        this.production = production;
    }*/

    @Override
    public String createTester() {
        return null;
    }
}

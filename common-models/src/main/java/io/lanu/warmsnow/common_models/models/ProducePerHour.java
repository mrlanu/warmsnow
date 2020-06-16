package io.lanu.warmsnow.common_models.models;

import io.lanu.warmsnow.common_models.FieldType;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class ProducePerHour {
    private Map<FieldType, Integer> goods;

    public ProducePerHour() {
        this.goods = new HashMap<>();
        this.goods.put(FieldType.WOOD, 10);
        this.goods.put(FieldType.CLAY, 10);
        this.goods.put(FieldType.IRON, 10);
        this.goods.put(FieldType.CROP, 10);
    }
}

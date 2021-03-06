package io.lanu.warmsnow.common_models.models;

import io.lanu.warmsnow.common_models.FieldType;
import lombok.Data;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Data
public class Warehouse {
    private Map<FieldType, BigDecimal> goods;

    public Warehouse() {
        this.goods = new HashMap<>();
        this.goods.put(FieldType.WOOD, BigDecimal.valueOf(750));
        this.goods.put(FieldType.CLAY, BigDecimal.valueOf(750));
        this.goods.put(FieldType.IRON, BigDecimal.valueOf(750));
        this.goods.put(FieldType.CROP, BigDecimal.valueOf(750));
    }

    public void addGood(FieldType fieldType, BigDecimal amount){
        goods.put(fieldType, goods.get(fieldType).add(amount));
    }

    public void addGoods(Map<FieldType, BigDecimal> resources){
        goods.forEach((k, v) -> goods.put(k, goods.get(k).add(resources.get(k))));
    }

    public void subtractGoods(Map<FieldType, BigDecimal> resources){
        goods.forEach((k, v) -> goods.put(k, goods.get(k).subtract(resources.get(k))));
    }
}

package io.lanu.warmsnow.templates.templates_client.models;

import lombok.Data;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Data
public class Warehouse {
    private Map<String, BigDecimal> goods;

    public Warehouse() {
        this.goods = new HashMap<>();
        this.goods.put("wood", BigDecimal.valueOf(750));
        this.goods.put("clay", BigDecimal.valueOf(750));
        this.goods.put("iron", BigDecimal.valueOf(750));
        this.goods.put("crop", BigDecimal.valueOf(750));
    }
}

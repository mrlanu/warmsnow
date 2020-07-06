package io.lanu.warmsnow.common_models.models;

import io.lanu.warmsnow.common_models.FieldType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Field {
    private int position;
    private int level;
    private FieldType fieldType;
    private int productivity;
    private boolean underUpgrade;
    private boolean ableToUpgrade;
    private long timeToNextLevel;
    private Map<FieldType, BigDecimal> resourcesToNextLevel;

    public void isUpgradable(Warehouse warehouse) {
        if (compareResources(this.getResourcesToNextLevel(), warehouse.getGoods())){
            this.setAbleToUpgrade(true);
        }else {
            this.setAbleToUpgrade(false);
        }
    }

    private boolean compareResources(Map<FieldType, BigDecimal> need, Map<FieldType, BigDecimal> available){
        for (FieldType key : need.keySet()){
            if (available.get(key).compareTo(need.get(key)) < 0){
                return false;
            }
        }
        return true;
    }
}

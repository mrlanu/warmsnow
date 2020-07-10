package io.lanu.warmsnow.common_models.models.buildings.requirements;

import io.lanu.warmsnow.common_models.FieldType;
import io.lanu.warmsnow.common_models.models.Warehouse;
import io.lanu.warmsnow.common_models.models.buildings.BuildingBase;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RequiredBase {

    List<BuildingBase> needBuildings;

    public RequiredBase() {
        this.needBuildings = new ArrayList<>();
    }

    public boolean check(List<BuildingBase> existBuildings, Warehouse warehouse, Map<FieldType, BigDecimal> needResources) {
        for (BuildingBase req : needBuildings){
            if (!existBuildings.contains(req)){
                return false;
            }
        }
        return compareResources(needResources, warehouse.getGoods());
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

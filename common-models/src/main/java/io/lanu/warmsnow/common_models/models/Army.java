package io.lanu.warmsnow.common_models.models;

import io.lanu.warmsnow.common_models.UnitType;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class Army {
    private Map<UnitType, Integer> homeLegion;

    public Army() {
        this.homeLegion = new HashMap<>();
    }
}

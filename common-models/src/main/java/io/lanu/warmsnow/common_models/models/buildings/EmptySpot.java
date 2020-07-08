package io.lanu.warmsnow.common_models.models.buildings;

import io.lanu.warmsnow.common_models.FieldType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
public class EmptySpot extends BuildingBase {
    public EmptySpot(int level, int position, Map<FieldType, Integer> resourcesToNextLevel, long timeToNextLevel) {
        super("empty-spot", level, position, resourcesToNextLevel, timeToNextLevel);
    }

    @Override
    public String createTester() {
        return null;
    }
}

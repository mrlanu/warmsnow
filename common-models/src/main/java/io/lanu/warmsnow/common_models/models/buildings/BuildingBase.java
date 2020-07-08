package io.lanu.warmsnow.common_models.models.buildings;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.lanu.warmsnow.common_models.FieldType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "buildingType")
@JsonSubTypes({
        @JsonSubTypes.Type(value = WarehouseBuilding.class, name = "warehouse"),
        @JsonSubTypes.Type(value = Granary.class, name= "granary"),
        @JsonSubTypes.Type(value = EmptySpot.class, name= "empty-spot"),
        @JsonSubTypes.Type(value = Barrack.class, name= "barrack"),
})
public abstract class BuildingBase {
    private String buildingType;
    private int level;
    private int position;
    private Map<FieldType, Integer> resourcesToNextLevel;
    private long timeToNextLevel;

    public BuildingBase(String buildingType, int level, int position, Map<FieldType, Integer> resourcesToNextLevel,
                        long timeToNextLevel) {
        this.buildingType = buildingType;
        this.level = level;
        this.position = position;
        this.resourcesToNextLevel = resourcesToNextLevel;
        this.timeToNextLevel = timeToNextLevel;
    }

    public boolean checkUpgrade(){
        // tester.test();
        String tester = createTester();
        System.out.println(tester);
        return true;
    }

    public abstract String createTester();
}

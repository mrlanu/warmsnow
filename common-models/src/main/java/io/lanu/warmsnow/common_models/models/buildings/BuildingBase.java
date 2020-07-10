package io.lanu.warmsnow.common_models.models.buildings;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.lanu.warmsnow.common_models.FieldType;
import io.lanu.warmsnow.common_models.models.BuildingType;
import io.lanu.warmsnow.common_models.models.Warehouse;
import io.lanu.warmsnow.common_models.models.buildings.requirements.RequiredBase;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Data
@NoArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "buildingType")
@JsonSubTypes({
        @JsonSubTypes.Type(value = WarehouseBuilding.class, name = "warehouse"),
        @JsonSubTypes.Type(value = Granary.class, name= "granary"),
        @JsonSubTypes.Type(value = EmptySpot.class, name= "empty"),
        @JsonSubTypes.Type(value = Barrack.class, name= "barrack"),
        @JsonSubTypes.Type(value = MainBuilding.class, name= "main")
})
public abstract class BuildingBase {
    private BuildingType buildingType;
    private int level;
    private int position;
    private Map<FieldType, BigDecimal> resourcesToNextLevel;
    private long timeToNextLevel;
    private boolean underUpgrade;
    private boolean ableToUpgrade;
    private boolean ableToBuild;

    public BuildingBase(BuildingType buildingType, int level, int position, Map<FieldType, BigDecimal> resourcesToNextLevel,
                        long timeToNextLevel) {
        this.buildingType = buildingType;
        this.level = level;
        this.position = position;
        this.resourcesToNextLevel = resourcesToNextLevel;
        this.timeToNextLevel = timeToNextLevel;
    }

    public boolean checkUpgrade(List<BuildingBase> existBuildings){
        return false;
    }

    public void couldBeBuild(List<BuildingBase> existBuildings,
                             Warehouse warehouse,
                             Map<FieldType, BigDecimal> needResources){
        RequiredBase tester = createTester();
        ableToBuild = tester.check(existBuildings, warehouse, needResources);
    }

    public abstract RequiredBase createTester();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BuildingBase that = (BuildingBase) o;
        return level == that.level &&
                buildingType == that.buildingType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(buildingType, level);
    }
}

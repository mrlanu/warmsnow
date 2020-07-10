package io.lanu.warmsnow.templates.templates_server.entities.buildings;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.lanu.warmsnow.common_models.FieldType;
import io.lanu.warmsnow.common_models.models.BuildingType;
import io.lanu.warmsnow.common_models.models.buildings.MainBuilding;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.Map;

@Data
@NoArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "buildingType")
@JsonSubTypes({
        @JsonSubTypes.Type(value = WarehouseBuildingTemplateEntity.class, name = "warehouse"),
        @JsonSubTypes.Type(value = GranaryTemplateEntity.class, name= "granary"),
        @JsonSubTypes.Type(value = EmptySpotTemplateEntity.class, name= "empty"),
        @JsonSubTypes.Type(value = BarrackTemplateEntity.class, name= "barrack"),
        @JsonSubTypes.Type(value = MainBuildingTemplateEntity.class, name= "main")
})
@Document("buildings")
@TypeAlias("building")
public abstract class BuildingBaseTemplateEntity {
    @Id
    private String id;
    private BuildingType buildingType;
    private int level;
    private int position;
    private Map<FieldType, BigDecimal> resourcesToNextLevel;
    private long timeToNextLevel;

    public BuildingBaseTemplateEntity(BuildingType buildingType, int level, int position, Map<FieldType, BigDecimal> resourcesToNextLevel,
                                      long timeToNextLevel) {
        this.buildingType = buildingType;
        this.level = level;
        this.position = position;
        this.resourcesToNextLevel = resourcesToNextLevel;
        this.timeToNextLevel = timeToNextLevel;
    }
}
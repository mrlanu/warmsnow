package io.lanu.warmsnow.villagesservice.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Document("prod-buildings")
@TypeAlias("prod-building")
@Data
public class BuildingEntity {
    @Id
    private String buildingId;
    private String villageId;
    private String type;
    private Integer requiresPopulation;
    private Map<String, Integer> constructionCost;
    private long constructionTime;
    private Map<Integer, Integer> produces;

    public BuildingEntity(String buildingId, String villageId, String type, Integer requiresPopulation,
                          Map<String, Integer> constructionCost, long constructionTime,
                          Map<Integer, Integer> produces) {
        this.buildingId = buildingId;
        this.villageId = villageId;
        this.type = type;
        this.requiresPopulation = requiresPopulation;
        this.constructionCost = constructionCost;
        this.constructionTime = constructionTime;
        this.produces = produces;
    }
}

package io.lanu.warmsnow.buildings_service.buildings_server.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Document("buildings")
@TypeAlias("building")
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

    public BuildingEntity(String villageId, String type, Integer requiresPopulation,
                          Map<String, Integer> constructionCost, long constructionTime,
                          Map<Integer, Integer> produces) {
        this.villageId = villageId;
        this.type = type;
        this.requiresPopulation = requiresPopulation;
        this.constructionCost = constructionCost;
        this.constructionTime = constructionTime;
        this.produces = produces;
    }
}

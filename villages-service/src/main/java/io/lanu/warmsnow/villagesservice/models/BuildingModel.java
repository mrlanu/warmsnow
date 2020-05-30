package io.lanu.warmsnow.villagesservice.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
public class BuildingModel {
    private String buildingId;
    private String villageId;
    private String type;
    private Integer requiresPopulation;
    private Map<String, Integer> constructionCost;
    private long constructionTime;
    private Map<Integer, Integer> produces;

    public BuildingModel(String villageId, String type, Integer requiresPopulation,
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

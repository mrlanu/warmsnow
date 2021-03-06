package io.lanu.warmsnow.templates.templates_client.dto;

import lombok.Data;

import java.util.Map;

@Data
public class BuildingDto {
    private String buildingId;
    private String villageId;
    private String type;
    private Integer requiresPopulation;
    private Map<String, Integer> constructionCost;
    private long constructionTime;
    private Map<Integer, Integer> produces;
    private boolean available;
}

package io.lanu.warmsnow.buildings_service.buildings_client.dto;

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
    private String testField;
}

package io.lanu.warmsnow.villagesservice.models;

import io.lanu.warmsnow.templates.templates_client.dto.VillageType;
import lombok.Data;

@Data
public class NewVillageRequest {
    private String accountId;
    private VillageType villageType;
    private Integer x;
    private Integer y;
}

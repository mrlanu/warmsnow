package io.lanu.warmsnow.common_models.requests;

import io.lanu.warmsnow.common_models.VillageType;
import lombok.Data;

@Data
public class NewVillageRequest {
    private String accountId;
    private VillageType villageType;
    private Integer x;
    private Integer y;
}

package io.lanu.warmsnow.templates.templates_client.dto;

import io.lanu.warmsnow.common_models.VillageType;
import io.lanu.warmsnow.common_models.models.Field;
import io.lanu.warmsnow.common_models.models.ProducePerHour;
import io.lanu.warmsnow.common_models.models.FieldTaskModel;
import io.lanu.warmsnow.common_models.models.Warehouse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VillageDto {
    private String villageId;
    private String accountId;
    private Integer x;
    private Integer y;
    private Integer culture;
    private Integer population;
    private VillageType villageType;
    private Warehouse warehouse;
    private ProducePerHour producePerHour;
    private List<Field> fields;
    private List<FieldTaskModel> tasks;
}

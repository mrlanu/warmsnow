package io.lanu.warmsnow.templates.templates_client.dto;

import io.lanu.warmsnow.templates.templates_client.models.Field;
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
    private VillageType villageType;
    private List<Field> fields;
}

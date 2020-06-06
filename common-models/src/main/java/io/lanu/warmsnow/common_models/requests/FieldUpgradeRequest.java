package io.lanu.warmsnow.common_models.requests;

import io.lanu.warmsnow.common_models.FieldType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FieldUpgradeRequest {
    private String villageId;
    private int position;
    private int level;
    private FieldType fieldType;
}

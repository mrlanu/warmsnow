package io.lanu.warmsnow.common_models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FieldUpgradeRequest {
    private int position;
    private int level;
    private FieldType fieldType;
}

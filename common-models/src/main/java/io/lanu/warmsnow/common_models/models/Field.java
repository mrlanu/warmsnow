package io.lanu.warmsnow.common_models.models;

import io.lanu.warmsnow.common_models.FieldType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Field {
    private int position;
    private int level;
    private FieldType fieldType;
    private BigDecimal productivity;
    private boolean underUpgrade;
    private boolean ableToUpgrade;
    private long timeToNextLevel;
    private Map<FieldType, BigDecimal> resourcesToNextLevel;
}

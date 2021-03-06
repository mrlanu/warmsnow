package io.lanu.warmsnow.templates.templates_client.dto;

import io.lanu.warmsnow.common_models.FieldType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FieldDto {
    private int position;
    private int level;
    private FieldType fieldType;
    private int productivity;
    private boolean underUpgrade;
    private boolean ableToUpgrade;
    private long timeToNextLevel;
    private Map<String, BigDecimal> resourcesToNextLevel;
}

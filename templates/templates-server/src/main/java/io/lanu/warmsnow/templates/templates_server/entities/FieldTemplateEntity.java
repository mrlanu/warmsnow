package io.lanu.warmsnow.templates.templates_server.entities;

import io.lanu.warmsnow.templates.templates_client.dto.FieldType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.Map;

@Data
@Document("fields-templates")
@NoArgsConstructor
@AllArgsConstructor
public class FieldTemplateEntity {
    private int position;
    private int level;
    private FieldType fieldType;
    private BigDecimal productivity;
    private boolean underUpgrade;
    private boolean ableToUpgrade;
    private long timeToNextLevel;
    private Map<String, BigDecimal> resourcesToNextLevel;
}

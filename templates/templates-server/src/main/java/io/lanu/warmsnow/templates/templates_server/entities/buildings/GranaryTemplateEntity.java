package io.lanu.warmsnow.templates.templates_server.entities.buildings;

import io.lanu.warmsnow.common_models.FieldType;
import io.lanu.warmsnow.common_models.models.BuildingType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
@Document("buildings")
@TypeAlias("granary")
public class GranaryTemplateEntity extends BuildingBaseTemplateEntity {
    private Integer capacity;

    public GranaryTemplateEntity(int level, int position, Map<FieldType, BigDecimal> resourcesToNextLevel,
                                 long timeToNextLevel, Integer capacity) {
        super(BuildingType.GRANARY, level, position, resourcesToNextLevel, timeToNextLevel);
        this.capacity = capacity;
    }
}

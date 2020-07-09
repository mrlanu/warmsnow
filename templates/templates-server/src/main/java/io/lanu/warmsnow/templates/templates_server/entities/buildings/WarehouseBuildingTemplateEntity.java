package io.lanu.warmsnow.templates.templates_server.entities.buildings;

import io.lanu.warmsnow.common_models.FieldType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
@Document("buildings")
@TypeAlias("warehouse")
public class WarehouseBuildingTemplateEntity extends BuildingBaseTemplateEntity {
    private Integer capacity;

    public WarehouseBuildingTemplateEntity(int level, int position, Map<FieldType, Integer> resourcesToNextLevel,
                                           long timeToNextLevel, Integer capacity) {
        super("warehouse", level, position, resourcesToNextLevel, timeToNextLevel);
        this.capacity = capacity;
    }
}

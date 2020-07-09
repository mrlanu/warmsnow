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
@TypeAlias("empty-spot")
public class EmptySpotTemplateEntity extends BuildingBaseTemplateEntity {
    public EmptySpotTemplateEntity(int level, int position, Map<FieldType, Integer> resourcesToNextLevel, long timeToNextLevel) {
        super("empty-spot", level, position, resourcesToNextLevel, timeToNextLevel);
    }
}

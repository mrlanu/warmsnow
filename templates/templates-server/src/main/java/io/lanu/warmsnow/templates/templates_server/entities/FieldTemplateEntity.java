package io.lanu.warmsnow.templates.templates_server.entities;

import io.lanu.warmsnow.templates.templates_client.dto.FieldType;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("fields-templates")
@NoArgsConstructor
public class FieldTemplateEntity {
    private int position;
    private int level;
    private FieldType fieldType;
    private int productivity;

    public FieldTemplateEntity(int position, int level, FieldType fieldType, int productivity) {
        this.position = position;
        this.level = level;
        this.fieldType = fieldType;
        this.productivity = productivity;
    }
}

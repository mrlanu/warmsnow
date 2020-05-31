package io.lanu.warmsnow.templates.templates_client.models;

import io.lanu.warmsnow.templates.templates_client.dto.FieldType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Field {
    private int position;
    private int level;
    private FieldType fieldType;
    private int productivity;
}

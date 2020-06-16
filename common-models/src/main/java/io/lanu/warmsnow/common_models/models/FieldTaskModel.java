package io.lanu.warmsnow.common_models.models;

import io.lanu.warmsnow.common_models.FieldType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;

@EqualsAndHashCode(callSuper = true)
@Data
public class FieldTaskModel extends TaskModel{
    private FieldType fieldType;
    private int position;
    private int level;
    private long timeLeft; // seconds

    public FieldTaskModel(String taskId, LocalDateTime execution, FieldType fieldType,
                          int position, int level, long timeLeft) {
        super(taskId, execution);
        this.fieldType = fieldType;
        this.position = position;
        this.level = level;
        this.timeLeft = timeLeft;
    }
}

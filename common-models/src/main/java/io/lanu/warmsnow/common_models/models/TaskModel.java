package io.lanu.warmsnow.common_models.models;

import io.lanu.warmsnow.common_models.FieldType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskModel {
    private String taskId;
    private FieldType fieldType;
    private int position;
    private int level;
    private LocalDateTime completedTime;
}

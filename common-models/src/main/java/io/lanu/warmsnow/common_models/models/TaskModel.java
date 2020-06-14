package io.lanu.warmsnow.common_models.models;

import io.lanu.warmsnow.common_models.FieldType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskModel {
    private String taskId;
    private FieldType fieldType;
    private int position;
    private int level;
    private LocalDateTime completedTime;
    private long timeLeft; // seconds

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskModel taskModel = (TaskModel) o;
        return Objects.equals(taskId, taskModel.taskId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskId);
    }
}

package io.lanu.warmsnow.common_models.models;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Objects;

@Data
public abstract class TaskModel {
    private String taskId;
    private LocalDateTime execution;

    public TaskModel(String taskId, LocalDateTime execution) {
        this.taskId = taskId;
        this.execution = execution;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskModel taskModel = (TaskModel) o;
        return taskId.equals(taskModel.taskId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskId);
    }
}

package io.lanu.warmsnow.villagesservice.models.tasks;

import io.lanu.warmsnow.villagesservice.entities.VillageEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseTask {

    private String taskId;
    private LocalDateTime executionTime;

    public BaseTask(LocalDateTime executionTime) {
        this.executionTime = executionTime;
    }

    public abstract void performActions(VillageEntity villageEntity);
}

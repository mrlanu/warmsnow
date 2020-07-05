package io.lanu.warmsnow.villagesservice.models.tasks;

import io.lanu.warmsnow.villagesservice.entities.VillageEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class LastTask extends BaseTask {

    public LastTask(LocalDateTime executionTime) {
        super(executionTime);
    }

    @Override
    public void performActions(VillageEntity villageEntity) {

    }
}

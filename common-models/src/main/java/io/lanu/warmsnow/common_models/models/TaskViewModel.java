package io.lanu.warmsnow.common_models.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskViewModel {
    private String taskId;
    private int position;
    private int level;
    private long timeLeft;

}

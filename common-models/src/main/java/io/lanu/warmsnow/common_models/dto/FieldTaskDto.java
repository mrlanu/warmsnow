package io.lanu.warmsnow.common_models.dto;


import io.lanu.warmsnow.common_models.models.Field;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class FieldTaskDto {
    private String taskId;
    private String villageId;
    private Field fieldOld;
    private Field fieldNew;
    private LocalDateTime executionTime;
    private long secondsLeft;
    private boolean paid;
}

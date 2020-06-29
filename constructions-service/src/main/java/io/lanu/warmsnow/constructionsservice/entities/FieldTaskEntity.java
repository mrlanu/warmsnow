package io.lanu.warmsnow.constructionsservice.entities;


import io.lanu.warmsnow.common_models.models.Field;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document("field-tasks")
@TypeAlias("field-tasks")
@Data
@NoArgsConstructor
public class FieldTaskEntity {
    @Id
    private String taskId;
    private String villageId;
    private Field fieldOld;
    private Field fieldNew;
    private LocalDateTime executionTime;
    private long secondsLeft;
    private boolean paid;

    public FieldTaskEntity(String villageId, Field fieldOld, Field fieldNew, LocalDateTime executionTime, long secondsLeft) {
        this.villageId = villageId;
        this.fieldOld = fieldOld;
        this.fieldNew = fieldNew;
        this.executionTime = executionTime;
        this.secondsLeft = secondsLeft;
    }
}

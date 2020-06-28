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
    private Field field;
    private LocalDateTime executionTime;

    public FieldTaskEntity(String villageId, Field field, LocalDateTime executionTime) {
        this.villageId = villageId;
        this.field = field;
        this.executionTime = executionTime;
    }
}

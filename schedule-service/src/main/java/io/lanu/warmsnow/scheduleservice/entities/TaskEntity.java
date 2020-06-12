package io.lanu.warmsnow.scheduleservice.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document("tasks")
@Data
@AllArgsConstructor
public class TaskEntity {

    @Id
    private String taskId;
    private String villageId;
}

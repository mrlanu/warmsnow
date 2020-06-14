package io.lanu.warmsnow.scheduleservice.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document("tasks")
@Data
@AllArgsConstructor
public class TaskEntity {

    @Id
    private String taskId;
    private String villageId;
    private int position;
    private int level;
    private long timeLeft; // seconds
}

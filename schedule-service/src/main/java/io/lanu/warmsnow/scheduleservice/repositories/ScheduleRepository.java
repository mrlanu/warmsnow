package io.lanu.warmsnow.scheduleservice.repositories;

import io.lanu.warmsnow.scheduleservice.entities.TaskEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ScheduleRepository extends MongoRepository<TaskEntity, String> {
    List<TaskEntity> findAllByVillageId(String villageId);
    void deleteByTaskId(String taskId);
}

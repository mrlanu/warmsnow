package io.lanu.warmsnow.scheduleservice.services;

import io.lanu.warmsnow.common_models.requests.FieldUpgradeRequest;

public interface ScheduleService {
    String scheduleTask(FieldUpgradeRequest fieldUpgradeRequest);
    //List<TaskViewModel> findAllTasksByVillageId(String villageId);
}

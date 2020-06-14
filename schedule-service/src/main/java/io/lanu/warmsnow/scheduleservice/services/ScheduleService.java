package io.lanu.warmsnow.scheduleservice.services;

import io.lanu.warmsnow.common_models.models.TaskViewModel;
import io.lanu.warmsnow.common_models.requests.FieldUpgradeRequest;

import java.util.List;

public interface ScheduleService {
    String scheduleTask(FieldUpgradeRequest fieldUpgradeRequest);
    List<TaskViewModel> findAllTasksByVillageId(String villageId);
}

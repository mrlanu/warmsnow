package io.lanu.warmsnow.scheduleservice.controllers;

import io.lanu.warmsnow.scheduleservice.models.TaskViewModel;
import io.lanu.warmsnow.common_models.requests.FieldUpgradeRequest;
import io.lanu.warmsnow.scheduleservice.services.ScheduleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @GetMapping("/{villageId}")
    public List<TaskViewModel> getTasksByVillageId(@PathVariable String villageId){
        return scheduleService.findAllTasksByVillageId(villageId);
    }

    @PostMapping("/fields")
    public ResponseEntity<String> scheduleTask(@RequestBody FieldUpgradeRequest request){
        String taskId = scheduleService.scheduleTask(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("Task id - " + taskId);
    }
}

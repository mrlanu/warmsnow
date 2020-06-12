package io.lanu.warmsnow.scheduleservice.controllers;

import io.lanu.warmsnow.common_models.requests.FieldUpgradeRequest;
import io.lanu.warmsnow.scheduleservice.services.ScheduleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping("/fields")
    public ResponseEntity<String> scheduleTask(@RequestBody FieldUpgradeRequest request){
        String taskId = scheduleService.scheduleTask(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("Task id - " + taskId);
    }
}

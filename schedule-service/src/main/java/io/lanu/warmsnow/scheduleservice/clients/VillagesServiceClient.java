package io.lanu.warmsnow.scheduleservice.clients;

import io.lanu.warmsnow.common_models.requests.FieldUpgradeRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "villages-service")
public interface VillagesServiceClient {

    /*@GetMapping("/{villageId}/tasks")
    List<TaskViewModel> getAllTasksByVillageId(@PathVariable String villageId);

    @PostMapping(value = "/tasks", consumes = "application/json")
    ResponseEntity<String> createTask(TaskRequestModel taskRequestModel);*/

    @PostMapping("/villages/fields/upgrade")
    void upgradeField(FieldUpgradeRequest upgradeRequest);
}

package io.lanu.warmsnow.villagesservice.clients;

import io.lanu.warmsnow.common_models.requests.FieldUpgradeRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "schedule-service")
public interface ScheduleServiceFeignClient {

    @PostMapping(value = "/schedule/fields/", consumes = "application/json")
    ResponseEntity<String> requestFieldUpgrade(@RequestBody FieldUpgradeRequest fieldUpgradeRequest);
}

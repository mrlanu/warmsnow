package io.lanu.warmsnow.villagesservice.clients;


import io.lanu.warmsnow.villagesservice.models.tasks.TroopTask;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "armies-service")
public interface ArmiesServiceFeignClient {

    @GetMapping(value = "/armies/{villageId}")
    List<TroopTask> getTasksByVillageId(@PathVariable("villageId") String villageId);
}

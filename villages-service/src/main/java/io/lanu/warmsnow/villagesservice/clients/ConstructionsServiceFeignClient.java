package io.lanu.warmsnow.villagesservice.clients;


import io.lanu.warmsnow.villagesservice.models.tasks.FieldTask;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "constructions-service")
public interface ConstructionsServiceFeignClient {

    @GetMapping(value = "/constructions/{villageId}/fields")
    List<FieldTask> getTasksByVillageId(@PathVariable("villageId") String villageId);
}

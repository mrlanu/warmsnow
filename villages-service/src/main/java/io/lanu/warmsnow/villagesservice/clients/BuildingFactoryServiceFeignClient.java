package io.lanu.warmsnow.villagesservice.clients;


import io.lanu.warmsnow.villagesservice.entities.BuildingEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "building-factory-service")
public interface BuildingFactoryServiceFeignClient {

    @GetMapping(value = "/building-factory/{buildingId}")
    BuildingEntity getBuilding(@PathVariable("buildingId") String buildingId);
}

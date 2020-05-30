package io.lanu.warmsnow.villagesservice.clients;


import io.lanu.warmsnow.buildings_service.buildings_client.dto.BuildingDto;
import io.lanu.warmsnow.buildings_service.buildings_client.dto.WarehouseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "buildings-server")
public interface BuildingsServiceFeignClient {

    @GetMapping(value = "/buildings/{buildingId}")
    BuildingDto getBuilding(@PathVariable("buildingId") String buildingId);

    @PostMapping(value = "/buildings/", consumes = "application/json")
    List<BuildingDto> getAvailableBuildings(@RequestBody WarehouseDto warehouse);
}

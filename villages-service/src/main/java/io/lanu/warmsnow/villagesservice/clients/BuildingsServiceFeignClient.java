package io.lanu.warmsnow.villagesservice.clients;


import io.lanu.warmsnow.templates.templates_client.dto.BuildingDto;
import io.lanu.warmsnow.templates.templates_client.dto.WarehouseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "templates-server")
public interface BuildingsServiceFeignClient {

    @GetMapping(value = "/templates/buildings/{buildingId}")
    BuildingDto getBuilding(@PathVariable("buildingId") String buildingId);

    @PostMapping(value = "/templates/buildings/", consumes = "application/json")
    List<BuildingDto> getAvailableBuildings(@RequestBody WarehouseDto warehouse);
}

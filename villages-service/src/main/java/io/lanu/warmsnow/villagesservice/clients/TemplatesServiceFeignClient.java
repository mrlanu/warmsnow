package io.lanu.warmsnow.villagesservice.clients;


import io.lanu.warmsnow.templates.templates_client.dto.VillageDto;
import io.lanu.warmsnow.templates.templates_client.dto.VillageType;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "templates-server")
public interface TemplatesServiceFeignClient {

    @GetMapping(value = "/templates/villages/{villageType}")
    VillageDto getVillageByType(@PathVariable("villageType") VillageType villageType);

    /*@GetMapping(value = "/templates/buildings/{buildingId}")
    BuildingDto getBuilding(@PathVariable("buildingId") String buildingId);

    @PostMapping(value = "/templates/buildings/", consumes = "application/json")
    List<BuildingDto> getAvailableBuildings(@RequestBody WarehouseDto warehouse);*/
}

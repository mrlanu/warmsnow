package io.lanu.warmsnow.villagesservice.clients;


import io.lanu.warmsnow.common_models.FieldType;
import io.lanu.warmsnow.common_models.VillageType;
import io.lanu.warmsnow.templates.templates_client.dto.FieldDto;
import io.lanu.warmsnow.templates.templates_client.dto.VillageDto;
import io.lanu.warmsnow.villagesservice.entities.VillageEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "templates-server")
public interface TemplatesServiceFeignClient {

    @GetMapping(value = "/templates/villages/{villageType}")
    VillageEntity getVillageByType(@PathVariable("villageType") VillageType villageType);

    @GetMapping(value = "/templates/fields/{fieldLevel}/{fieldType}")
    FieldDto getFieldByLevelAndType(@PathVariable("fieldLevel") int fieldLevel,
                                    @PathVariable("fieldType") FieldType fieldType);

    /*@GetMapping(value = "/templates/buildings/{buildingId}")
    BuildingDto getBuilding(@PathVariable("buildingId") String buildingId);

    @PostMapping(value = "/templates/buildings/", consumes = "application/json")
    List<BuildingDto> getAvailableBuildings(@RequestBody WarehouseDto warehouse);*/
}

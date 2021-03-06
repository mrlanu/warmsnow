package io.lanu.warmsnow.armiesservice.clients;


import io.lanu.warmsnow.common_models.UnitType;
import io.lanu.warmsnow.templates.templates_client.dto.UnitDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "templates-server")
public interface TemplatesServiceFeignClient {

    @GetMapping(value = "/templates/units/{unitType}")
    UnitDto getUnitByType(@PathVariable("unitType") UnitType unitType);

    /*@GetMapping(value = "/templates/fields/{fieldLevel}/{fieldType}")
    FieldDto getFieldByLevelAndType(@PathVariable("fieldLevel") int fieldLevel,
                                    @PathVariable("fieldType") FieldType fieldType);*/

    /*@GetMapping(value = "/templates/buildings/{buildingId}")
    BuildingDto getBuilding(@PathVariable("buildingId") String buildingId);

    @PostMapping(value = "/templates/buildings/", consumes = "application/json")
    List<BuildingDto> getAvailableBuildings(@RequestBody WarehouseDto warehouse);*/
}

package io.lanu.warmsnow.templates.templates_server.controllers;


import io.lanu.warmsnow.templates.templates_client.dto.BuildingDto;
import io.lanu.warmsnow.templates.templates_client.dto.WarehouseDto;
import io.lanu.warmsnow.templates.templates_server.entities.BuildingEntity;
import io.lanu.warmsnow.templates.templates_server.services.BuildingsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/buildings")
public class BuildingsController {

    private BuildingsService buildingsService;

    public BuildingsController(BuildingsService buildingsService) {
        this.buildingsService = buildingsService;
    }

    @PostMapping
    public List<BuildingDto> getAllAvailable(@RequestBody WarehouseDto warehouseDto){
        return buildingsService.getAllAvailable(warehouseDto);
    }

    @GetMapping("/{buildingId}")
    public BuildingEntity getBuildingById(@PathVariable  String buildingId){
        return buildingsService.findById(buildingId).orElseThrow(RuntimeException::new);
    }

}

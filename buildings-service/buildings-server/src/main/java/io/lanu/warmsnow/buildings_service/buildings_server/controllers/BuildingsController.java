package io.lanu.warmsnow.buildings_service.buildings_server.controllers;


import io.lanu.warmsnow.buildings_service.buildings_client.dto.BuildingDto;
import io.lanu.warmsnow.buildings_service.buildings_server.entities.BuildingEntity;
import io.lanu.warmsnow.buildings_service.buildings_server.services.BuildingsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BuildingsController {

    private BuildingsService buildingsService;

    public BuildingsController(BuildingsService buildingsService) {
        this.buildingsService = buildingsService;
    }

    @GetMapping
    public List<BuildingDto> getAll(){
        return buildingsService.findAll();
    }

    @GetMapping("/{buildingId}")
    public BuildingEntity getBuildingById(@PathVariable  String buildingId){
        return buildingsService.findById(buildingId).orElseThrow(RuntimeException::new);
    }

}

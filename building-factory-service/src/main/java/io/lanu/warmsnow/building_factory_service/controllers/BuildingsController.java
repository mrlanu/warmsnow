package io.lanu.warmsnow.building_factory_service.controllers;


import io.lanu.warmsnow.building_factory_service.entities.BuildingEntity;
import io.lanu.warmsnow.building_factory_service.services.BuildingsService;
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
    public List<BuildingEntity> getAll(){
        return buildingsService.findAll();
    }

    @GetMapping("/{buildingId}")
    public BuildingEntity getBuildingById(@PathVariable  String buildingId){
        return buildingsService.findById(buildingId).orElseThrow(RuntimeException::new);
    }

}
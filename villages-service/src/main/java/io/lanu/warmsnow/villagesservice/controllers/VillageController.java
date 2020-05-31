package io.lanu.warmsnow.villagesservice.controllers;

import io.lanu.warmsnow.templates.templates_client.dto.BuildingDto;
import io.lanu.warmsnow.villagesservice.entities.VillageEntity;
import io.lanu.warmsnow.villagesservice.services.VillageService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class VillageController {

    private VillageService villageService;

    public VillageController(VillageService villageService) {
        this.villageService = villageService;
    }

    @GetMapping
    public List<VillageEntity> getAll(){
        return villageService.findAll();
    }

    @PostMapping("/create")
    public VillageEntity newVillage(@RequestParam("accountId") String accountId){
        return villageService.createVillage(accountId);
    }

    @PostMapping("/buildings/create")
    public VillageEntity addNewBuilding(@RequestBody BuildingDto buildingDto){
        return villageService.addNewBuilding(buildingDto);
    }

    @GetMapping("/{villageId}/buildings/available")
    public List<BuildingDto> getAvailableBuildings(@PathVariable String villageId){
        return villageService.getAvailableBuildings(villageId);
    }

    @GetMapping("/buildings/{buildingId}")
    public BuildingDto getBuildingById(@PathVariable String buildingId){
        return villageService.getBuildingById(buildingId);
    }
}

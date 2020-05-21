package io.lanu.warmsnow.villagesservice.controllers;

import io.lanu.warmsnow.villagesservice.entities.BuildingEntity;
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
    public VillageEntity createBuilding(@RequestBody BuildingEntity buildingEntity){
        return villageService.createBuilding(buildingEntity);
    }
}

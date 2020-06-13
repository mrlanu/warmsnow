package io.lanu.warmsnow.villagesservice.controllers;

import io.lanu.warmsnow.common_models.requests.FieldUpgradeRequest;
import io.lanu.warmsnow.templates.templates_client.dto.VillageDto;
import io.lanu.warmsnow.villagesservice.entities.VillageEntity;
import io.lanu.warmsnow.common_models.requests.NewVillageRequest;
import io.lanu.warmsnow.villagesservice.services.VillageService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class VillageController {

    private final VillageService villageService;

    public VillageController(VillageService villageService) {
        this.villageService = villageService;
    }

    @GetMapping("/{villageId}")
    public VillageDto getVillageById(@PathVariable String villageId){
        return villageService.getVillageById(villageId);
    }

    @PostMapping("/create")
    public void newVillage(@RequestBody NewVillageRequest newVillageRequest){
        villageService.createVillage(newVillageRequest);
    }

    @PostMapping("/fields/schedule-upgrade")
    public void scheduleFieldUpgrade(@RequestBody FieldUpgradeRequest request){
        villageService.scheduleFieldUpgrade(request);
    }

    @PostMapping("/fields/upgrade")
    public void upgradeField(@RequestBody FieldUpgradeRequest request){
        villageService.upgradeField(request);
    }

}

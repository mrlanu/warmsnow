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

    @GetMapping
    public List<VillageEntity> getAll(){
        return villageService.findAll();
    }

    @GetMapping("/{villageId}")
    public VillageDto getVillageById(@PathVariable String villageId){
        return villageService.findById(villageId);
    }

    @PostMapping("/create")
    public VillageDto newVillage(@RequestBody NewVillageRequest newVillageRequest){
        return villageService.createVillage(newVillageRequest);
    }

    @PutMapping("/fields/upgrade-request")
    public void upgradeFieldRequest(@RequestBody FieldUpgradeRequest request){
        villageService.upgradeFieldRequest(request);
    }
}

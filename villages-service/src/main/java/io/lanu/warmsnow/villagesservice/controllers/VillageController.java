package io.lanu.warmsnow.villagesservice.controllers;

import io.lanu.warmsnow.common_models.models.buildings.Barrack;
import io.lanu.warmsnow.common_models.models.buildings.BuildingBase;
import io.lanu.warmsnow.common_models.models.buildings.Granary;
import io.lanu.warmsnow.common_models.models.buildings.WarehouseBuilding;
import io.lanu.warmsnow.common_models.requests.NewVillageRequest;
import io.lanu.warmsnow.templates.templates_client.dto.VillageDto;
import io.lanu.warmsnow.villagesservice.entities.VillageEntity;
import io.lanu.warmsnow.villagesservice.services.VillageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
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

    @PostMapping("/create-new-village")
    public ResponseEntity<String> newVillage(@RequestBody NewVillageRequest newVillageRequest){
        VillageEntity villageEntity = villageService.createVillage(newVillageRequest);
        return ResponseEntity.status(HttpStatus.OK).body("New Village ID : " + villageEntity.getVillageId());
    }

    @GetMapping("/buildings")
    public List<BuildingBase> getBuildings(){
        return Arrays.asList(
                new WarehouseBuilding(1, 0, null, 30, 200),
                new Granary(1, 1, null, 30, 100),
                new Barrack(1, 2, null, 30, 50));
    }

    @PostMapping("/buildings")
    public void  testBuilding(@RequestBody BuildingBase buildingBase){
        System.out.println(buildingBase);
        buildingBase.checkUpgrade();
    }
}

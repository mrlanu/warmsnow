package io.lanu.warmsnow.villagesservice.controllers;

import io.lanu.warmsnow.common_models.requests.FieldUpgradeRequest;
import io.lanu.warmsnow.common_models.requests.NewVillageRequest;
import io.lanu.warmsnow.templates.templates_client.dto.VillageDto;
import io.lanu.warmsnow.villagesservice.entities.VillageEntity;
import io.lanu.warmsnow.villagesservice.services.VillageService;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/fields/schedule-upgrade")
    public void scheduleFieldUpgrade(@RequestBody FieldUpgradeRequest request){
        villageService.scheduleFieldUpgrade(request);
    }

    // not directly call from browser (will call with Feign client from schedule-service)
    @PostMapping("/fields/upgrade")
    public void upgradeField(@RequestBody FieldUpgradeRequest request){
        villageService.upgradeField(request);
    }

}

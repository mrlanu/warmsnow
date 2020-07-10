package io.lanu.warmsnow.villagesservice.controllers;

import io.lanu.warmsnow.common_models.models.buildings.BuildingBase;
import io.lanu.warmsnow.common_models.requests.NewVillageRequest;
import io.lanu.warmsnow.templates.templates_client.dto.VillageDto;
import io.lanu.warmsnow.villagesservice.clients.TemplatesServiceFeignClient;
import io.lanu.warmsnow.villagesservice.entities.VillageEntity;
import io.lanu.warmsnow.villagesservice.services.VillageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class VillageController {

    private final VillageService villageService;
    private final TemplatesServiceFeignClient templatesServiceFeignClient;

    public VillageController(VillageService villageService, TemplatesServiceFeignClient templatesServiceFeignClient) {
        this.villageService = villageService;
        this.templatesServiceFeignClient = templatesServiceFeignClient;
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

    @GetMapping("/{villageId}/buildings")
    public List<BuildingBase> getAllBuildings(@PathVariable String villageId){
        return villageService.getAvailableBuildings(villageId);
    }
}

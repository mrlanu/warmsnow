package io.lanu.warmsnow.constructionsservice.controllers;

import io.lanu.warmsnow.common_models.dto.FieldTaskDto;
import io.lanu.warmsnow.common_models.requests.FieldUpgradeRequest;
import io.lanu.warmsnow.constructionsservice.services.ConstructionsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ConstructionsController {

    private final ConstructionsService constructionsService;

    public ConstructionsController(ConstructionsService constructionsService) {
        this.constructionsService = constructionsService;
    }

    @PostMapping("/fields/schedule-upgrade")
    public void scheduleFieldUpgrade(@RequestBody FieldUpgradeRequest request){
        constructionsService.scheduleFieldUpgrade(request);
    }

    @GetMapping("/{villageId}/fields")
    public List<FieldTaskDto> getAllTasksByVillageId(@PathVariable String villageId){
        return constructionsService.getAllTasksByVillageId(villageId);
    }

}

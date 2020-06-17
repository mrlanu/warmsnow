package io.lanu.warmsnow.armiesservice.controllers;

import io.lanu.warmsnow.armiesservice.models.ArmyOrderRequest;
import io.lanu.warmsnow.armiesservice.services.ArmiesService;
import io.lanu.warmsnow.common_models.UnitType;
import io.lanu.warmsnow.templates.templates_client.dto.UnitDto;
import org.springframework.web.bind.annotation.*;

@RestController
public class ArmiesController {

    private final ArmiesService armiesService;

    public ArmiesController(ArmiesService armiesService) {
        this.armiesService = armiesService;
    }

    @GetMapping("/{unitType}")
    public UnitDto getUnitByType(@PathVariable UnitType unitType) {
        return armiesService.getUnitByType(unitType);
    }

    @PostMapping
    public void orderArmyUnits(@RequestBody ArmyOrderRequest armyOrderRequest) {

    }
}

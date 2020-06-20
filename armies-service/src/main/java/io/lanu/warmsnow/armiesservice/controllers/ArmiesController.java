package io.lanu.warmsnow.armiesservice.controllers;

import io.lanu.warmsnow.armiesservice.entities.ArmyOrderEntity;
import io.lanu.warmsnow.armiesservice.models.ArmyOrderRequest;
import io.lanu.warmsnow.armiesservice.services.ArmiesService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ArmiesController {

    private final ArmiesService armiesService;

    public ArmiesController(ArmiesService armiesService) {
        this.armiesService = armiesService;
    }

    @PostMapping
    public ArmyOrderEntity orderArmyUnits(@RequestBody ArmyOrderRequest armyOrderRequest) {
        return armiesService.orderUnits(armyOrderRequest);
    }
}

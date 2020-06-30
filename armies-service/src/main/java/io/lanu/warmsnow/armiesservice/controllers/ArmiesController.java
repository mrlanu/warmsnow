package io.lanu.warmsnow.armiesservice.controllers;

import io.lanu.warmsnow.armiesservice.entities.ArmyOrderEntity;
import io.lanu.warmsnow.armiesservice.models.ArmyOrderRequest;
import io.lanu.warmsnow.armiesservice.services.ArmiesService;
import io.lanu.warmsnow.common_models.dto.TroopTaskDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ArmiesController {

    private final ArmiesService armiesService;

    public ArmiesController(ArmiesService armiesService) {
        this.armiesService = armiesService;
    }

    @GetMapping("/{villageId}")
    public List<TroopTaskDto> getTroopTasksFromOrders(@PathVariable String villageId){
        return armiesService.getTroopTasksFromOrders(villageId);
    }

    @PostMapping
    public ArmyOrderEntity orderArmyUnits(@RequestBody ArmyOrderRequest armyOrderRequest) {
        return armiesService.orderUnits(armyOrderRequest);
    }
}

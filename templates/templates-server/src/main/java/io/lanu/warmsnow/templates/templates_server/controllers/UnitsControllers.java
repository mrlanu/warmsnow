package io.lanu.warmsnow.templates.templates_server.controllers;

import io.lanu.warmsnow.common_models.UnitType;
import io.lanu.warmsnow.templates.templates_server.entities.UnitTemplateEntity;
import io.lanu.warmsnow.templates.templates_server.services.UnitsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/units")
public class UnitsControllers {

    private final UnitsService unitsService;

    public UnitsControllers(UnitsService unitsService) {
        this.unitsService = unitsService;
    }

    @GetMapping("/{unitType}")
    public UnitTemplateEntity getByUnitType(@PathVariable UnitType unitType){
        return unitsService.findByUnitType(unitType);
    }

}

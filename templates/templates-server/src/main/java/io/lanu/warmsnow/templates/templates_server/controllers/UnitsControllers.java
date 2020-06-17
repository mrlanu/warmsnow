package io.lanu.warmsnow.templates.templates_server.controllers;

import io.lanu.warmsnow.common_models.UnitType;
import io.lanu.warmsnow.templates.templates_client.dto.UnitDto;
import io.lanu.warmsnow.templates.templates_server.services.UnitsService;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/units")
public class UnitsControllers {

    private final UnitsService unitsService;
    private final ModelMapper MAPPER = new ModelMapper();

    public UnitsControllers(UnitsService unitsService) {
        this.unitsService = unitsService;
    }

    @GetMapping("/{unitType}")
    public UnitDto getByUnitType(@PathVariable UnitType unitType){
        return MAPPER.map(unitsService.findByUnitType(unitType), UnitDto.class);
    }

}

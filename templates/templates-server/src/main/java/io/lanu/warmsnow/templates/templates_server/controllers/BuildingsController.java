package io.lanu.warmsnow.templates.templates_server.controllers;

import io.lanu.warmsnow.templates.templates_server.entities.buildings.BuildingBaseTemplateEntity;
import io.lanu.warmsnow.templates.templates_server.services.BuildingsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/buildings")
public class BuildingsController {

    private final BuildingsService buildingsService;

    public BuildingsController(BuildingsService buildingsService) {
        this.buildingsService = buildingsService;
    }

    @GetMapping
    public List<BuildingBaseTemplateEntity> getAllBuildings(){
        return buildingsService.getAllBuildings();
    }
}

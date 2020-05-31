package io.lanu.warmsnow.templates.templates_server.controllers;

import io.lanu.warmsnow.templates.templates_client.dto.VillageType;
import io.lanu.warmsnow.templates.templates_server.entities.VillageTemplateEntity;
import io.lanu.warmsnow.templates.templates_server.services.VillagesService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/villages")
public class VillagesController {

    private final VillagesService villagesService;

    public VillagesController(VillagesService villagesService) {
        this.villagesService = villagesService;
    }

    @GetMapping("/{villageType}")
    public VillageTemplateEntity getVillageByType(@PathVariable VillageType villageType){
        return villagesService.findByVillageType(villageType);
    }
}

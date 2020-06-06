package io.lanu.warmsnow.templates.templates_server.controllers;

import io.lanu.warmsnow.common_models.VillageType;
import io.lanu.warmsnow.templates.templates_client.dto.VillageDto;
import io.lanu.warmsnow.templates.templates_server.entities.VillageTemplateEntity;
import io.lanu.warmsnow.templates.templates_server.services.VillagesService;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/villages")
public class VillagesController {

    private final VillagesService villagesService;
    private final ModelMapper mapper = new ModelMapper();

    public VillagesController(VillagesService villagesService) {
        this.villagesService = villagesService;
    }

    @GetMapping("/{villageType}")
    public VillageDto getVillageByType(@PathVariable VillageType villageType){
        VillageTemplateEntity villageTemplateEntity = villagesService.findByVillageType(villageType);
        return mapper.map(villageTemplateEntity, VillageDto.class);
    }
}

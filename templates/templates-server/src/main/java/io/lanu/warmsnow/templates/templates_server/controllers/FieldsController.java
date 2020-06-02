package io.lanu.warmsnow.templates.templates_server.controllers;

import io.lanu.warmsnow.templates.templates_client.dto.FieldType;
import io.lanu.warmsnow.templates.templates_server.entities.FieldTemplateEntity;
import io.lanu.warmsnow.templates.templates_server.services.FieldsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fields")
public class FieldsController {

    private final FieldsService fieldsService;

    public FieldsController(FieldsService fieldsService) {
        this.fieldsService = fieldsService;
    }

    @GetMapping("/{level}/{type}")
    public FieldTemplateEntity getFieldByLevelAndType(@PathVariable Integer level, @PathVariable FieldType type){
        return fieldsService.findByLevelAndFieldType(level, type);
    }
}

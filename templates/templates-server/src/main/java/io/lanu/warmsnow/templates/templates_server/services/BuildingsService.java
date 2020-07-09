package io.lanu.warmsnow.templates.templates_server.services;

import io.lanu.warmsnow.templates.templates_server.entities.buildings.BuildingBaseTemplateEntity;

import java.util.List;

public interface BuildingsService {

    List<BuildingBaseTemplateEntity> getAllBuildings();
    List<BuildingBaseTemplateEntity> saveAll(List<BuildingBaseTemplateEntity> list);
}

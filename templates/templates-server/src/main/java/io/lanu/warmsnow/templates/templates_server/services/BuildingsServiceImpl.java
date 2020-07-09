package io.lanu.warmsnow.templates.templates_server.services;

import io.lanu.warmsnow.templates.templates_server.entities.buildings.BuildingBaseTemplateEntity;
import io.lanu.warmsnow.templates.templates_server.repositories.BuildingsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BuildingsServiceImpl implements BuildingsService {

    private final BuildingsRepository buildingsRepository;

    public BuildingsServiceImpl(BuildingsRepository buildingsRepository) {
        this.buildingsRepository = buildingsRepository;
    }

    @Override
    public List<BuildingBaseTemplateEntity> getAllBuildings() {
        return buildingsRepository.getAllByLevel(1);
    }

    @Override
    public List<BuildingBaseTemplateEntity> saveAll(List<BuildingBaseTemplateEntity> list) {
        return buildingsRepository.saveAll(list);
    }
}

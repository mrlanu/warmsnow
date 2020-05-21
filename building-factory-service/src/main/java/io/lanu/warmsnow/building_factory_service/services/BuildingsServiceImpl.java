package io.lanu.warmsnow.building_factory_service.services;

import io.lanu.warmsnow.building_factory_service.entities.BuildingEntity;
import io.lanu.warmsnow.building_factory_service.repositories.BuildingsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class BuildingsServiceImpl implements BuildingsService {

    private BuildingsRepository buildingsRepository;

    public BuildingsServiceImpl(BuildingsRepository buildingsRepository) {
        this.buildingsRepository = buildingsRepository;
    }

    @Override
    public BuildingEntity save(BuildingEntity buildingEntity) {
        return buildingsRepository.save(buildingEntity);
    }

    @Override
    public Optional<BuildingEntity> findById(String buildingId) {
        return buildingsRepository.findById(buildingId);
    }

    @Override
    public List<BuildingEntity> findAll() {
        return buildingsRepository.findAll();
    }
}

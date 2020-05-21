package io.lanu.warmsnow.building_factory_service.services;

import io.lanu.warmsnow.building_factory_service.entities.BuildingEntity;

import java.util.List;
import java.util.Optional;

public interface BuildingsService {
   BuildingEntity save(BuildingEntity buildingEntity);
   Optional<BuildingEntity> findById(String buildingId);
   List<BuildingEntity> findAll();
}

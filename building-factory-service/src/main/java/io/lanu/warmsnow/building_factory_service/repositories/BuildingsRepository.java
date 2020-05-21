package io.lanu.warmsnow.building_factory_service.repositories;


import io.lanu.warmsnow.building_factory_service.entities.BuildingEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BuildingsRepository extends MongoRepository<BuildingEntity, String> {
    BuildingEntity findByType(String type);
}

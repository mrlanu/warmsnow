package io.lanu.warmsnow.templates.templates_server.repositories;


import io.lanu.warmsnow.templates.templates_server.entities.BuildingEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BuildingsRepository extends MongoRepository<BuildingEntity, String> {
    BuildingEntity findByType(String type);
}

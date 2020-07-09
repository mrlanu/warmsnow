package io.lanu.warmsnow.templates.templates_server.repositories;


import io.lanu.warmsnow.templates.templates_server.entities.buildings.BuildingBaseTemplateEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BuildingsRepository extends MongoRepository<BuildingBaseTemplateEntity, String> {
    List<BuildingBaseTemplateEntity> getAllByLevel(int level);
}

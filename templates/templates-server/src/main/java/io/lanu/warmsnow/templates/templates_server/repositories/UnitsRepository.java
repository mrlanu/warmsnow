package io.lanu.warmsnow.templates.templates_server.repositories;

import io.lanu.warmsnow.common_models.UnitType;
import io.lanu.warmsnow.templates.templates_server.entities.UnitTemplateEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UnitsRepository extends MongoRepository<UnitTemplateEntity, String> {
    UnitTemplateEntity findByUnitType(UnitType unitType);
}

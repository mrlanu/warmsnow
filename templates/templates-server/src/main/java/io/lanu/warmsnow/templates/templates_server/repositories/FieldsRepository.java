package io.lanu.warmsnow.templates.templates_server.repositories;

import io.lanu.warmsnow.common_models.FieldType;
import io.lanu.warmsnow.templates.templates_server.entities.FieldTemplateEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FieldsRepository extends MongoRepository<FieldTemplateEntity, String> {
    FieldTemplateEntity findByLevelAndFieldType(int level, FieldType fieldType);
}

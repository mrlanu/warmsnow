package io.lanu.warmsnow.templates.templates_server.services;

import io.lanu.warmsnow.templates.templates_client.dto.FieldType;
import io.lanu.warmsnow.templates.templates_server.entities.FieldTemplateEntity;

import java.util.List;

public interface FieldsService {
    List<FieldTemplateEntity> findAll();
    void saveAll(List<FieldTemplateEntity> fields);
    FieldTemplateEntity findByLevelAndFieldType(int level, FieldType fieldType);
}

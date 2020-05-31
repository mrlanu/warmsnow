package io.lanu.warmsnow.templates.templates_server.services;

import io.lanu.warmsnow.templates.templates_client.dto.FieldType;
import io.lanu.warmsnow.templates.templates_server.entities.FieldTemplateEntity;
import io.lanu.warmsnow.templates.templates_server.repositories.FieldsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FieldsServiceImpl implements FieldsService {

    private final FieldsRepository fieldsRepository;

    public FieldsServiceImpl(FieldsRepository fieldsRepository) {
        this.fieldsRepository = fieldsRepository;
    }

    @Override
    public List<FieldTemplateEntity> findAll() {
        return fieldsRepository.findAll();
    }

    @Override
    public FieldTemplateEntity findByLevelAndFieldType(int level, FieldType fieldType) {
        return fieldsRepository.findByLevelAndFieldType(level, fieldType);
    }

    @Override
    public void saveAll(List<FieldTemplateEntity> fields) {
        fieldsRepository.saveAll(fields);
    }
}

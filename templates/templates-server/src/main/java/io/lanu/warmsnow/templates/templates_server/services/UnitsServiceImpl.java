package io.lanu.warmsnow.templates.templates_server.services;

import io.lanu.warmsnow.common_models.UnitType;
import io.lanu.warmsnow.templates.templates_server.entities.UnitTemplateEntity;
import io.lanu.warmsnow.templates.templates_server.repositories.UnitsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UnitsServiceImpl implements UnitsService {

    private final UnitsRepository unitsRepository;


    public UnitsServiceImpl(UnitsRepository unitsRepository) {
        this.unitsRepository = unitsRepository;
    }

    @Override
    public List<UnitTemplateEntity> findAll() {
        return unitsRepository.findAll();
    }

    @Override
    public UnitTemplateEntity save(UnitTemplateEntity entity) {
        return unitsRepository.save(entity);
    }

    @Override
    public UnitTemplateEntity findByUnitType(UnitType unitType) {
        return unitsRepository.findByUnitType(unitType);
    }
}

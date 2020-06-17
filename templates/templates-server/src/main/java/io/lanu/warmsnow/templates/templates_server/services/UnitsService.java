package io.lanu.warmsnow.templates.templates_server.services;

import io.lanu.warmsnow.common_models.UnitType;
import io.lanu.warmsnow.templates.templates_server.entities.UnitTemplateEntity;

import java.util.List;

public interface UnitsService {
    List<UnitTemplateEntity> findAll();
    UnitTemplateEntity save(UnitTemplateEntity entity);
    UnitTemplateEntity findByUnitType(UnitType unitType);
}

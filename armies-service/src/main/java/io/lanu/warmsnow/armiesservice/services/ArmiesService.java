package io.lanu.warmsnow.armiesservice.services;

import io.lanu.warmsnow.common_models.UnitType;
import io.lanu.warmsnow.templates.templates_client.dto.UnitDto;

public interface ArmiesService {
    UnitDto getUnitByType(UnitType unitType);
}

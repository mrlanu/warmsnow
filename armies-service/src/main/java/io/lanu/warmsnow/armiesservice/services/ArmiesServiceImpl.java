package io.lanu.warmsnow.armiesservice.services;

import io.lanu.warmsnow.armiesservice.clients.TemplatesServiceFeignClient;
import io.lanu.warmsnow.common_models.UnitType;
import io.lanu.warmsnow.templates.templates_client.dto.UnitDto;
import org.springframework.stereotype.Service;

@Service
public class ArmiesServiceImpl implements ArmiesService {

    private final TemplatesServiceFeignClient feignClient;

    public ArmiesServiceImpl(TemplatesServiceFeignClient feignClient) {
        this.feignClient = feignClient;
    }

    @Override
    public UnitDto getUnitByType(UnitType unitType) {
        return feignClient.getUnitByType(unitType);
    }
}

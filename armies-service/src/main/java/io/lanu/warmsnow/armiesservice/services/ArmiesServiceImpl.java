package io.lanu.warmsnow.armiesservice.services;

import io.lanu.warmsnow.armiesservice.clients.TemplatesServiceFeignClient;
import io.lanu.warmsnow.armiesservice.entities.ArmyOrderEntity;
import io.lanu.warmsnow.armiesservice.models.ArmyOrderRequest;
import io.lanu.warmsnow.armiesservice.repositories.ArmyOrdersRepository;
import io.lanu.warmsnow.common_models.UnitType;
import io.lanu.warmsnow.templates.templates_client.dto.UnitDto;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArmiesServiceImpl implements ArmiesService {

    private final TemplatesServiceFeignClient feignClient;
    private final ArmyOrdersRepository armyOrdersRepository;

    public ArmiesServiceImpl(TemplatesServiceFeignClient feignClient, ArmyOrdersRepository armyOrdersRepository) {
        this.feignClient = feignClient;
        this.armyOrdersRepository = armyOrdersRepository;
    }

    @Override
    public ArmyOrderEntity orderUnits(ArmyOrderRequest armyOrderRequest) {
        UnitDto template = getUnitByType(armyOrderRequest.getUnitType());

        List<ArmyOrderEntity> ordersList = armyOrdersRepository
                .findAllByVillageId(armyOrderRequest.getVillageId())
                .stream()
                .sorted(Comparator.comparing(ArmyOrderEntity::getCreated))
                .collect(Collectors.toList());

        LocalDateTime lastTime = ordersList.size() > 0 ? ordersList.get(ordersList.size() - 1).getEndOrderTime() : LocalDateTime.now();

        LocalDateTime endOrderTime = lastTime.plus(
                armyOrderRequest.getAmount() * template.getBaseProductionTime(), ChronoUnit.SECONDS);

        ArmyOrderEntity armyOrder = new ArmyOrderEntity(armyOrderRequest.getVillageId(), lastTime, armyOrderRequest.getUnitType(),
                armyOrderRequest.getAmount(), template.getBaseProductionTime(), endOrderTime);

        return armyOrdersRepository.save(armyOrder);
    }

    private UnitDto getUnitByType(UnitType unitType) {
        return feignClient.getUnitByType(unitType);
    }
}

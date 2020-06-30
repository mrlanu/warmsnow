package io.lanu.warmsnow.armiesservice.services;

import io.lanu.warmsnow.armiesservice.clients.TemplatesServiceFeignClient;
import io.lanu.warmsnow.armiesservice.entities.ArmyOrderEntity;
import io.lanu.warmsnow.armiesservice.models.ArmyOrderRequest;
import io.lanu.warmsnow.armiesservice.repositories.ArmyOrdersRepository;
import io.lanu.warmsnow.common_models.UnitType;
import io.lanu.warmsnow.common_models.dto.TroopTaskDto;
import io.lanu.warmsnow.templates.templates_client.dto.UnitDto;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
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
        UnitDto template = feignClient.getUnitByType(armyOrderRequest.getUnitType());
        List<ArmyOrderEntity> ordersList = getAllOrdersByVillageId(armyOrderRequest.getVillageId());

        LocalDateTime lastTime = ordersList.size() > 0 ? ordersList.get(ordersList.size() - 1).getEndOrderTime() : LocalDateTime.now();

        LocalDateTime endOrderTime = lastTime.plus(
                armyOrderRequest.getAmount() * template.getBaseProductionTime(), ChronoUnit.SECONDS);

        ArmyOrderEntity armyOrder = new ArmyOrderEntity(armyOrderRequest.getVillageId(), lastTime, armyOrderRequest.getUnitType(),
                armyOrderRequest.getAmount(), template.getBaseProductionTime(), template.getEatHour(), endOrderTime);

        return armyOrdersRepository.save(armyOrder);
    }

    @Override
    public List<ArmyOrderEntity> getAllOrdersByVillageId(String villageId){
        return armyOrdersRepository
                .findAllByVillageId(villageId)
                .stream()
                .sorted(Comparator.comparing(ArmyOrderEntity::getCreated))
                .collect(Collectors.toList());
    }

    @Override
    public List<TroopTaskDto> getTroopTasksFromOrders(String villageId) {
        List<TroopTaskDto> result = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        List<ArmyOrderEntity> ordersList = getAllOrdersByVillageId(villageId);

        if (ordersList.size() > 0) {
            for (ArmyOrderEntity order : ordersList) {
                long duration = Duration.between(order.getLastTime(), now).toSeconds();

                if (now.isAfter(order.getEndOrderTime())) {
                    // add all troops from order to result list
                    result.addAll(addCompletedTroops(order, order.getLeftTrain()));
                    armyOrdersRepository.deleteById(order.getId());
                    continue;
                }

                int completedTroops = (int) (duration / order.getDurationEach());

                if (completedTroops > 0) {
                    // add completed troops from order to result list
                    result.addAll(addCompletedTroops(order, completedTroops));
                    order.setLeftTrain(order.getLeftTrain() - completedTroops);
                    order.setLastTime(order.getLastTime().plus(completedTroops * order.getDurationEach(), ChronoUnit.SECONDS));
                    armyOrdersRepository.save(order);
                }
            }
        }
        return result;
    }

    private List<TroopTaskDto> addCompletedTroops(ArmyOrderEntity order, Integer amount) {
        List<TroopTaskDto> result = new ArrayList<>();
        LocalDateTime exec = order.getLastTime();
        for (int i = 0; i < amount; i++) {
            exec = exec.plus(order.getDurationEach(), ChronoUnit.SECONDS);
            UnitType unitType = order.getUnitType();
            result.add(new TroopTaskDto(order.getVillageId(), order.getUnitType(), order.getEatHour(), exec));
        }
        return result;
    }

}

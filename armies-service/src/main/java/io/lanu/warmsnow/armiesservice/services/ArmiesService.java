package io.lanu.warmsnow.armiesservice.services;

import io.lanu.warmsnow.armiesservice.entities.ArmyOrderEntity;
import io.lanu.warmsnow.armiesservice.models.ArmyOrderRequest;
import io.lanu.warmsnow.common_models.dto.TroopTaskDto;

import java.util.List;

public interface ArmiesService {
    List<ArmyOrderEntity> getAllOrdersByVillageId(String villageId);
    ArmyOrderEntity orderUnits(ArmyOrderRequest armyOrderRequest);
    List<TroopTaskDto> getTroopTasksFromOrders(String villageId);
}

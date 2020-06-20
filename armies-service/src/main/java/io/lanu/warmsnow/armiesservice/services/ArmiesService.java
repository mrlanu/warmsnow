package io.lanu.warmsnow.armiesservice.services;

import io.lanu.warmsnow.armiesservice.entities.ArmyOrderEntity;
import io.lanu.warmsnow.armiesservice.models.ArmyOrderRequest;

public interface ArmiesService {
    ArmyOrderEntity orderUnits(ArmyOrderRequest armyOrderRequest);
}

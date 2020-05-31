package io.lanu.warmsnow.buildings_service.buildings_server.services;

import io.lanu.warmsnow.buildings_service.buildings_client.dto.BuildingDto;
import io.lanu.warmsnow.buildings_service.buildings_client.dto.WarehouseDto;
import io.lanu.warmsnow.buildings_service.buildings_server.entities.BuildingEntity;

import java.util.List;
import java.util.Optional;

public interface BuildingsService {
   BuildingEntity save(BuildingEntity buildingEntity);
   Optional<BuildingEntity> findById(String buildingId);
   List<BuildingDto> findAll();
   List<BuildingDto> getAllAvailable(WarehouseDto warehouseDto);
}

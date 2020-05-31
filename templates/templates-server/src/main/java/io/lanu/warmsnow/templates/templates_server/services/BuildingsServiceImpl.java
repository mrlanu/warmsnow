package io.lanu.warmsnow.templates.templates_server.services;

import io.lanu.warmsnow.templates.templates_client.dto.BuildingDto;
import io.lanu.warmsnow.templates.templates_client.dto.WarehouseDto;
import io.lanu.warmsnow.templates.templates_server.entities.BuildingEntity;
import io.lanu.warmsnow.templates.templates_server.repositories.BuildingsRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BuildingsServiceImpl implements BuildingsService {

    private final BuildingsRepository buildingsRepository;
    private final ModelMapper MAPPER = new ModelMapper();

    public BuildingsServiceImpl(BuildingsRepository buildingsRepository) {
        this.buildingsRepository = buildingsRepository;
    }

    @Override
    public BuildingEntity save(BuildingEntity buildingEntity) {
        return buildingsRepository.save(buildingEntity);
    }

    @Override
    public Optional<BuildingEntity> findById(String buildingId) {
        return buildingsRepository.findById(buildingId);
    }

    @Override
    public List<BuildingDto> findAll() {
        final ModelMapper mapper = new ModelMapper();
        return buildingsRepository.findAll()
                .stream()
                .map(buildingEntity -> mapper.map(buildingEntity, BuildingDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<BuildingDto> getAllAvailable(WarehouseDto warehouseDto) {
        return buildingsRepository.findAll()
                .stream()
                .filter(buildingEntity -> isAvailable(buildingEntity, warehouseDto))
                .peek(buildingEntity -> buildingEntity.setAvailable(true))
                .map(buildingEntity -> MAPPER.map(buildingEntity, BuildingDto.class))
                .collect(Collectors.toList());
    }

    private boolean isAvailable(BuildingEntity buildingEntity, WarehouseDto warehouseDto){
        /*boolean coins = buildingEntity.getConstructionCost()
                .getOrDefault("coins", 0) <= warehouseDto.getCoins();
        boolean foods = buildingEntity.getConstructionCost()
                .getOrDefault("foods", 0) <= warehouseDto.getFoods();
        return coins && foods;*/
        return false;
    }
}

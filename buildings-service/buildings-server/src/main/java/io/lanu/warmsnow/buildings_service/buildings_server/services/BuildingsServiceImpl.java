package io.lanu.warmsnow.buildings_service.buildings_server.services;

import io.lanu.warmsnow.buildings_service.buildings_client.dto.BuildingDto;
import io.lanu.warmsnow.buildings_service.buildings_server.entities.BuildingEntity;
import io.lanu.warmsnow.buildings_service.buildings_server.repositories.BuildingsRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BuildingsServiceImpl implements BuildingsService {

    private BuildingsRepository buildingsRepository;

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
}

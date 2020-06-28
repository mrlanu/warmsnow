package io.lanu.warmsnow.constructionsservice.services;

import io.lanu.warmsnow.common_models.dto.FieldTaskDto;
import io.lanu.warmsnow.common_models.models.Field;
import io.lanu.warmsnow.common_models.requests.FieldUpgradeRequest;
import io.lanu.warmsnow.constructionsservice.clients.TemplatesServiceFeignClient;
import io.lanu.warmsnow.constructionsservice.entities.FieldTaskEntity;
import io.lanu.warmsnow.constructionsservice.repositories.ConstructionsRepository;
import io.lanu.warmsnow.templates.templates_client.dto.FieldDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConstructionsServiceImpl implements ConstructionsService {

    private final TemplatesServiceFeignClient templateServiceClient;
    private final ConstructionsRepository constructionsRepository;
    private final ModelMapper MAPPER = new ModelMapper();

    public ConstructionsServiceImpl(TemplatesServiceFeignClient templateServiceClient, ConstructionsRepository constructionsRepository) {
        this.templateServiceClient = templateServiceClient;
        this.constructionsRepository = constructionsRepository;
    }

    @Override
    public void scheduleFieldUpgrade(FieldUpgradeRequest request) {
        // fetch template and increment one level
        FieldDto upgradedFieldDto = templateServiceClient.
                getFieldByLevelAndType(request.getField().getLevel() + 1, request.getField().getFieldType());

        // map FieldDto to Field
        Field upgradedField = MAPPER.
                map(upgradedFieldDto, Field.class);

        // new entity, execution time from the request
        FieldTaskEntity fieldTaskEntity = new FieldTaskEntity(request.getVillageId(), upgradedField,
                LocalDateTime.now().plus(request.getField().getTimeToNextLevel(), ChronoUnit.SECONDS));

        constructionsRepository.save(fieldTaskEntity);
    }

    @Override
    public List<FieldTaskDto> getAllTasksByVillageId(String villageId) {
        List<FieldTaskDto> result = constructionsRepository.findAllByVillageId(villageId)
                .stream()
                .map(village -> MAPPER.map(village, FieldTaskDto.class))
                .collect(Collectors.toList());
        // delete all tasks before now
        constructionsRepository.deleteAllByVillageIdAndExecutionTimeBefore(villageId, LocalDateTime.now());
        return result;
    }
}

package io.lanu.warmsnow.villagesservice.services;

import io.lanu.warmsnow.common_models.FieldType;
import io.lanu.warmsnow.common_models.models.Army;
import io.lanu.warmsnow.common_models.models.Field;
import io.lanu.warmsnow.common_models.models.ProducePerHour;
import io.lanu.warmsnow.common_models.models.Warehouse;
import io.lanu.warmsnow.villagesservice.clients.ArmiesServiceFeignClient;
import io.lanu.warmsnow.villagesservice.clients.ConstructionsServiceFeignClient;
import io.lanu.warmsnow.villagesservice.clients.TemplatesServiceFeignClient;
import io.lanu.warmsnow.villagesservice.entities.VillageEntity;
import io.lanu.warmsnow.villagesservice.repositories.VillageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.*;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServiceBaseTest {
    @Captor
    ArgumentCaptor<VillageEntity> villageEntityArgumentCaptor;
    @Mock
    ArmiesServiceFeignClient armiesClient;
    @Mock
    ConstructionsServiceFeignClient constructionClient;
    @Mock
    VillageRepository villageRepository;
    @Mock
    TemplatesServiceFeignClient templatesFeignClient;
    @InjectMocks
    VillageServiceImpl villageService;
    @InjectMocks
    VillageViewBuilderImpl villageViewBuilder;

    @BeforeEach
    void init(){
        MockitoAnnotations.initMocks(this);
    }

    VillageEntity createVillageEntity(LocalDateTime nowTime){
        VillageEntity villageEntity = new VillageEntity();

        ProducePerHour producePerHour = new ProducePerHour();
        Map<FieldType, Integer> productions = new HashMap<>();
        productions.put(FieldType.CROP, 10);
        productions.put(FieldType.CLAY, 10);
        productions.put(FieldType.IRON, 10);
        productions.put(FieldType.WOOD, 10);
        producePerHour.setGoods(productions);

        Warehouse warehouse = new Warehouse();
        Map<FieldType, BigDecimal> onWarehouse = new HashMap<>();
        onWarehouse.put(FieldType.CROP, BigDecimal.valueOf(100));
        onWarehouse.put(FieldType.CLAY, BigDecimal.valueOf(100));
        onWarehouse.put(FieldType.IRON, BigDecimal.valueOf(100));
        onWarehouse.put(FieldType.WOOD, BigDecimal.valueOf(100));
        warehouse.setGoods(onWarehouse);

        Map<FieldType, BigDecimal> resourcesToNextLevel = Map.of(
                FieldType.WOOD, BigDecimal.valueOf(50),
                FieldType.CLAY, BigDecimal.valueOf(50),
                FieldType.IRON, BigDecimal.valueOf(50),
                FieldType.CROP, BigDecimal.valueOf(50));

        List<Field> fields = Arrays.asList(
                new Field(0, 1, FieldType.WOOD, 10,
                        false, false, 30, resourcesToNextLevel),
                new Field(1, 1, FieldType.CLAY, 10,
                        false, false, 30, resourcesToNextLevel),
                new Field(2, 1, FieldType.IRON, 10,
                        false, false, 30, resourcesToNextLevel),
                new Field(3, 1, FieldType.CROP, 10,
                        false, false, 30, resourcesToNextLevel)
        );

        villageEntity.setWarehouse(warehouse);
        villageEntity.setProducePerHour(producePerHour);
        villageEntity.setModified(nowTime);
        villageEntity.setArmy(new Army());
        villageEntity.setFields(fields);

        return villageEntity;
    }
}

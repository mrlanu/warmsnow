package io.lanu.warmsnow.villagesservice.services;

import io.lanu.warmsnow.common_models.FieldType;
import io.lanu.warmsnow.common_models.UnitType;
import io.lanu.warmsnow.common_models.models.Army;
import io.lanu.warmsnow.common_models.models.Field;
import io.lanu.warmsnow.common_models.models.ProducePerHour;
import io.lanu.warmsnow.common_models.models.Warehouse;
import io.lanu.warmsnow.templates.templates_client.dto.VillageDto;
import io.lanu.warmsnow.villagesservice.clients.ArmiesServiceFeignClient;
import io.lanu.warmsnow.villagesservice.clients.ConstructionsServiceFeignClient;
import io.lanu.warmsnow.villagesservice.entities.VillageEntity;
import io.lanu.warmsnow.villagesservice.models.tasks.FieldTask;
import io.lanu.warmsnow.villagesservice.models.tasks.TroopTask;
import io.lanu.warmsnow.villagesservice.repositories.VillageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import org.mockito.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

class VillageViewBuilderImplTest {


    @Captor
    private ArgumentCaptor<VillageEntity> villageEntityArgumentCaptor;
    @Mock
    private ArmiesServiceFeignClient armiesClient;
    @Mock
    private ConstructionsServiceFeignClient constructionClient;
    @Mock
    private VillageRepository villageRepository;
    @InjectMocks
    private VillageViewBuilderImpl villageViewBuilder;

    @BeforeEach
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void build() {
        LocalDateTime nowTime = LocalDateTime.now().minusHours(12);
        VillageEntity villageEntity = createVillageEntity(nowTime);
        given(this.villageRepository.findById(ArgumentMatchers.any())).willReturn(Optional.of(villageEntity));

        List<FieldTask> fieldTasks = new ArrayList<>();
        given(this.constructionClient.getTasksByVillageId(ArgumentMatchers.any())).willReturn(fieldTasks);

        List<TroopTask> armyTasks = Arrays.asList(
                new TroopTask(nowTime.plusHours(1), UnitType.LEGIONNAIRE, 10),
                new TroopTask(nowTime.plusHours(2), UnitType.LEGIONNAIRE, 10));
        given(this.armiesClient.getTasksByVillageId(ArgumentMatchers.any())).willReturn(armyTasks);

        // when
        VillageDto villageDto = villageViewBuilder.build("test");

        // then
        then(villageRepository).should().save(villageEntityArgumentCaptor.capture());
        VillageEntity villageEntityArgCaptorVal = villageEntityArgumentCaptor.getValue();
        assertThat(villageEntityArgCaptorVal).isEqualTo(villageEntity);

        System.out.println(villageDto.getArmy());
        System.out.println(villageDto.getWarehouse());
        System.out.println(villageDto.getProducePerHour());
        assertEquals(BigDecimal.valueOf(10), villageDto.getWarehouse().getGoods().get(FieldType.CROP));
    }

    @Test
    void build_ShouldProduceSomeGoods(){
        LocalDateTime nowTime = LocalDateTime.now().minusHours(10);
        VillageEntity villageEntity = createVillageEntity(nowTime);
        given(this.villageRepository.findById(ArgumentMatchers.any())).willReturn(Optional.of(villageEntity));

        // when
        VillageDto villageDto = villageViewBuilder.build("test");

        // then
        assertEquals(BigDecimal.valueOf(200), villageDto.getWarehouse().getGoods().get(FieldType.CROP));
        assertEquals(BigDecimal.valueOf(200), villageDto.getWarehouse().getGoods().get(FieldType.WOOD));
        assertEquals(BigDecimal.valueOf(200), villageDto.getWarehouse().getGoods().get(FieldType.CLAY));
        assertEquals(BigDecimal.valueOf(200), villageDto.getWarehouse().getGoods().get(FieldType.IRON));
        System.out.println(villageDto.getWarehouse());
        System.out.println(villageDto.getProducePerHour());
    }

    @Test
    void build_ShouldUpgradeTwoFields(){
        LocalDateTime nowTime = LocalDateTime.now().minusHours(10);
        VillageEntity villageEntity = createVillageEntity(nowTime);
        given(this.villageRepository.findById(ArgumentMatchers.any())).willReturn(Optional.of(villageEntity));

        Field fieldOldCrop = new Field(3, 1, FieldType.CROP, 10, false,
                false, 60, Map.of(
                FieldType.WOOD, BigDecimal.valueOf(50),
                FieldType.CLAY, BigDecimal.valueOf(50),
                FieldType.IRON, BigDecimal.valueOf(50),
                FieldType.CROP, BigDecimal.valueOf(50)));
        Field fieldNewCrop = new Field(3, 2, FieldType.CROP, 50, false,
                false, 300, Map.of(
                FieldType.WOOD, BigDecimal.valueOf(150),
                FieldType.CLAY, BigDecimal.valueOf(150),
                FieldType.IRON, BigDecimal.valueOf(150),
                FieldType.CROP, BigDecimal.valueOf(150)));
        Field fieldOldWood = new Field(0, 1, FieldType.WOOD, 10, false,
                false, 60, Map.of(
                FieldType.WOOD, BigDecimal.valueOf(50),
                FieldType.CLAY, BigDecimal.valueOf(50),
                FieldType.IRON, BigDecimal.valueOf(50),
                FieldType.CROP, BigDecimal.valueOf(50)));
        Field fieldNewWood = new Field(0, 2, FieldType.WOOD, 50, false,
                false, 300, Map.of(
                FieldType.WOOD, BigDecimal.valueOf(150),
                FieldType.CLAY, BigDecimal.valueOf(150),
                FieldType.IRON, BigDecimal.valueOf(150),
                FieldType.CROP, BigDecimal.valueOf(150)));
        List<FieldTask> fieldTasks = Arrays.asList(
                new FieldTask(nowTime.plusHours(5), "test", fieldOldCrop, fieldNewCrop, false),
                new FieldTask(nowTime.plusHours(9), "test", fieldOldWood, fieldNewWood, false));
        given(this.constructionClient.getTasksByVillageId(ArgumentMatchers.any())).willReturn(fieldTasks);

        // when
        VillageDto villageDto = villageViewBuilder.build("test");

        // then
        assertEquals(2, villageDto.getFields().get(0).getLevel());
        assertEquals(2, villageDto.getFields().get(3).getLevel());
        assertTrue(villageDto.getFields().get(1).isAbleToUpgrade());
        assertFalse(villageDto.getFields().get(0).isAbleToUpgrade());
        assertEquals(BigDecimal.valueOf(300.0), villageDto.getWarehouse().getGoods().get(FieldType.CROP));
        assertEquals(BigDecimal.valueOf(140.0), villageDto.getWarehouse().getGoods().get(FieldType.WOOD));
        System.out.println(villageDto.getFields());
        System.out.println(villageDto.getWarehouse());
        System.out.println(villageDto.getProducePerHour());
    }

    private VillageEntity createVillageEntity(LocalDateTime nowTime){
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

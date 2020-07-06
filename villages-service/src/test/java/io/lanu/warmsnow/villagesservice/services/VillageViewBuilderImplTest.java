package io.lanu.warmsnow.villagesservice.services;

import io.lanu.warmsnow.common_models.FieldType;
import io.lanu.warmsnow.common_models.UnitType;
import io.lanu.warmsnow.common_models.models.Army;
import io.lanu.warmsnow.common_models.models.ProducePerHour;
import io.lanu.warmsnow.common_models.models.Warehouse;
import io.lanu.warmsnow.templates.templates_client.dto.VillageDto;
import io.lanu.warmsnow.villagesservice.clients.ArmiesServiceFeignClient;
import io.lanu.warmsnow.villagesservice.clients.ConstructionsServiceFeignClient;
import io.lanu.warmsnow.villagesservice.entities.VillageEntity;
import io.lanu.warmsnow.villagesservice.models.tasks.FieldTask;
import io.lanu.warmsnow.villagesservice.models.tasks.TroopTask;
import io.lanu.warmsnow.villagesservice.repositories.VillageRepository;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

class VillageViewBuilderImplTest {

    private ArmiesServiceFeignClient armiesClient = Mockito.mock(ArmiesServiceFeignClient.class);
    private ConstructionsServiceFeignClient constructionClient = Mockito.mock(ConstructionsServiceFeignClient.class);
    private VillageRepository villageRepository = Mockito.mock(VillageRepository.class);
    private VillageViewBuilderImpl villageViewBuilder = Mockito
            .spy(new VillageViewBuilderImpl(villageRepository, constructionClient, armiesClient));

    @Test
    void build() {
        LocalDateTime nowTime = LocalDateTime.now().minusHours(12);
        VillageEntity villageEntity = createVillageEntity(nowTime);
        Mockito.doReturn(Optional.of(villageEntity)).when(this.villageRepository).findById(ArgumentMatchers.any());
        Mockito.doReturn(null).when(this.villageRepository).save(ArgumentMatchers.any());

        List<FieldTask> fieldTasks = new ArrayList<>();
        Mockito.doReturn(fieldTasks).when(this.constructionClient).getTasksByVillageId(ArgumentMatchers.any());
        List<TroopTask> armyTasks = Arrays.asList(
                new TroopTask(nowTime.plusHours(1), UnitType.LEGIONNAIRE, 10),
                new TroopTask(nowTime.plusHours(2), UnitType.LEGIONNAIRE, 10));
        Mockito.doReturn(armyTasks).when(this.armiesClient).getTasksByVillageId(ArgumentMatchers.any());

        VillageDto villageDto = villageViewBuilder.build("test");
        System.out.println(villageDto.getArmy());
        System.out.println(villageDto.getWarehouse());
        System.out.println(villageDto.getProducePerHour());
        assertEquals(BigDecimal.valueOf(50), villageDto.getWarehouse().getGoods().get(FieldType.CROP));
    }

    private VillageEntity createVillageEntity(LocalDateTime nowTime){
        VillageEntity villageEntity = new VillageEntity();

        ProducePerHour producePerHour = new ProducePerHour();
        Map<FieldType, Integer> productions = new HashMap<>();
        productions.put(FieldType.CROP, 0);
        productions.put(FieldType.CLAY, 0);
        productions.put(FieldType.IRON, 0);
        productions.put(FieldType.WOOD, 0);
        producePerHour.setGoods(productions);

        Warehouse warehouse = new Warehouse();
        Map<FieldType, BigDecimal> onWarehouse = new HashMap<>();
        onWarehouse.put(FieldType.CROP, BigDecimal.valueOf(100));
        onWarehouse.put(FieldType.CLAY, BigDecimal.valueOf(100));
        onWarehouse.put(FieldType.IRON, BigDecimal.valueOf(100));
        onWarehouse.put(FieldType.WOOD, BigDecimal.valueOf(100));
        warehouse.setGoods(onWarehouse);

        villageEntity.setWarehouse(warehouse);
        villageEntity.setProducePerHour(producePerHour);
        villageEntity.setModified(nowTime);
        villageEntity.setArmy(new Army());
        villageEntity.setFields(new ArrayList<>());

        return villageEntity;
    }
}

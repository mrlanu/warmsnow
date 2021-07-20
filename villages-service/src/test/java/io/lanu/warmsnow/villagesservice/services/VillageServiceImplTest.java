package io.lanu.warmsnow.villagesservice.services;

import io.lanu.warmsnow.common_models.FieldType;
import io.lanu.warmsnow.common_models.models.buildings.Barrack;
import io.lanu.warmsnow.common_models.models.buildings.BuildingBase;
import io.lanu.warmsnow.common_models.models.buildings.Granary;
import io.lanu.warmsnow.common_models.models.buildings.WarehouseBuilding;
import io.lanu.warmsnow.villagesservice.entities.VillageEntity;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

class VillageServiceImplTest extends ServiceBaseTest{

    @Test
    void getAllAvailableBuildings() {
        Map<FieldType, BigDecimal> toNextLevel = Map.of(
                FieldType.WOOD, BigDecimal.valueOf(20),
                FieldType.CLAY, BigDecimal.valueOf(20),
                FieldType.IRON, BigDecimal.valueOf(20),
                FieldType.CROP, BigDecimal.valueOf(20));
        List<BuildingBase> buildingsList = Arrays.asList(
                new WarehouseBuilding(1, 0, toNextLevel, 60, 750),
                new Barrack(1, 0, toNextLevel, 60, 1),
                new Granary(1, 0, toNextLevel, 60, 750)
        );
        given(templatesFeignClient.getAllBuildings()).willReturn(buildingsList);

        LocalDateTime nowTime = LocalDateTime.now().minusHours(12);
        VillageEntity villageEntity = createVillageEntity(nowTime);
        given(this.villageRepository.findById(ArgumentMatchers.any())).willReturn(Optional.of(villageEntity));

        // when
        // get all buildings
        List<BuildingBase> buildings = villageService.getAvailableBuildings("test");

        // then
        assertEquals(3, buildings.size());

        List<BuildingBase> ableToBuildList = buildings.stream().filter(BuildingBase::isAbleToBuild).collect(Collectors.toList());
        List<BuildingBase> unableToBuildList = buildings.stream().filter(b -> !b.isAbleToBuild()).collect(Collectors.toList());
        assertEquals(2, ableToBuildList.size());
        assertEquals(1, unableToBuildList.size());
    }
}

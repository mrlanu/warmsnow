package io.lanu.warmsnow.villagesservice.services;

import io.lanu.warmsnow.common_models.FieldType;
import io.lanu.warmsnow.common_models.models.buildings.Barrack;
import io.lanu.warmsnow.common_models.models.buildings.BuildingBase;
import io.lanu.warmsnow.common_models.models.buildings.Granary;
import io.lanu.warmsnow.common_models.models.buildings.WarehouseBuilding;
import io.lanu.warmsnow.villagesservice.entities.VillageEntity;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

class VillageServiceImplTest extends ServiceBaseTest{

    @Test
    void getAllAvailableBuildings() {
        Map<FieldType, Integer> toNextLevel = Map.of(
                FieldType.WOOD, 200,
                FieldType.CLAY, 200,
                FieldType.IRON, 200,
                FieldType.CROP, 200);
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
        List<BuildingBase> buildings = villageService.getAvailableBuildings("test");

        // then
        System.out.println(buildings);
        assertEquals(3, buildings.size());
    }
}

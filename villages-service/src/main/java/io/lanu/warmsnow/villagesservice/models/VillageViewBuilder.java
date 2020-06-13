package io.lanu.warmsnow.villagesservice.models;

import io.lanu.warmsnow.common_models.FieldType;
import io.lanu.warmsnow.common_models.models.Field;
import io.lanu.warmsnow.templates.templates_client.dto.VillageDto;
import io.lanu.warmsnow.villagesservice.entities.VillageEntity;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class VillageViewBuilder implements Builder {

    private VillageEntity villageEntity;
    private final ModelMapper MAPPER = new ModelMapper();


    public VillageViewBuilder(VillageEntity villageEntity) {
        this.villageEntity = villageEntity;
    }

    @Override
    public void reset() {

    }

    public void checkFieldsUpgradable(){
        villageEntity.getFields()
                .forEach(field -> {
                    if (compareResources(field.getResourcesToNextLevel(), villageEntity.getWarehouse().getGoods())){
                        field.setAbleToUpgrade(true);
                    }
                });
    }

    private boolean compareResources(Map<FieldType, BigDecimal> need, Map<FieldType, BigDecimal> available){
        for (FieldType key : need.keySet()){
            if (available.get(key).compareTo(need.get(key)) < 0){
                return false;
            }
        }
        return true;
    }


    @Override
    public void calculateProducedGoods(){

        Long durationFromLastModified = Duration
                .between(villageEntity.getModified(), LocalDateTime.now()).toMillis();

        Map<FieldType, Double> productionPerHour = villageEntity.getFields()
                .stream()
                .collect(Collectors.groupingBy(Field::getFieldType,
                        Collectors.summingDouble(buildingView -> buildingView.getProductivity().doubleValue())));

        MathContext mc = new MathContext(3);
        BigDecimal wood =
                new BigDecimal((durationFromLastModified * productionPerHour.get(FieldType.WOOD)) / 3600000L, mc);
        BigDecimal clay =
                new BigDecimal((durationFromLastModified * productionPerHour.get(FieldType.CLAY)) / 3600000L, mc);
        BigDecimal iron =
                new BigDecimal((durationFromLastModified * productionPerHour.get(FieldType.IRON)) / 3600000L, mc);
        BigDecimal crop =
                new BigDecimal((durationFromLastModified * productionPerHour.get(FieldType.CROP)) / 3600000L, mc);

        Map<FieldType, BigDecimal> goods = villageEntity.getWarehouse().getGoods();
        goods.put(FieldType.WOOD, goods.get(FieldType.WOOD).add(wood));
        goods.put(FieldType.CLAY, goods.get(FieldType.CLAY).add(clay));
        goods.put(FieldType.IRON, goods.get(FieldType.IRON).add(iron));
        goods.put(FieldType.CROP, goods.get(FieldType.CROP).add(crop));

        log.info("Produced resources added to the Warehouse.");
    }

    public VillageDto getProduct(){
        VillageDto result = MAPPER.map(villageEntity, VillageDto.class);
        reset();
        return result;
    }
}

package io.lanu.warmsnow.villagesservice.models;

import io.lanu.warmsnow.common_models.FieldType;
import io.lanu.warmsnow.common_models.models.Field;
import io.lanu.warmsnow.villagesservice.entities.VillageEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;


@Data
@NoArgsConstructor
public class FieldTask implements TaskExecution {

    private String taskId;
    private String villageId;
    private Field fieldOld;
    private Field fieldNew;
    private LocalDateTime executionTime;
    private boolean paid;

    @Override
    public void executeTask(VillageEntity villageEntity) {
        // upgrade the Field
        fieldNew.setUnderUpgrade(false);
        villageEntity.getFields().set(fieldNew.getPosition(), fieldNew);
        // recalculate production per hour
        /*Map<FieldType, Integer> productionPerHour = villageEntity.getFields()
                .stream()
                .collect(Collectors.groupingBy(Field::getFieldType,
                        Collectors.summingInt(Field::getProductivity)));*/
        // set production to village
        /*villageEntity.getProducePerHour().setGoods(productionPerHour);*/
        villageEntity.addToProducePerHour(fieldNew.getFieldType(), fieldNew.getProductivity() - fieldOld.getProductivity());
    }
}

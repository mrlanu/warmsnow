package io.lanu.warmsnow.villagesservice.models.tasks;

import io.lanu.warmsnow.common_models.models.Field;
import io.lanu.warmsnow.villagesservice.entities.VillageEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class FieldTask extends BaseTask {

    private String villageId;
    private Field fieldOld;
    private Field fieldNew;
    private boolean paid;

    public FieldTask(LocalDateTime executionTime, String villageId, Field fieldOld, Field fieldNew, boolean paid) {
        super(executionTime);
        this.villageId = villageId;
        this.fieldOld = fieldOld;
        this.fieldNew = fieldNew;
        this.paid = paid;
    }

    @Override
    public void performActions(VillageEntity villageEntity) {
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
        villageEntity.getProducePerHour().addGood(fieldNew.getFieldType(), fieldNew.getProductivity() - fieldOld.getProductivity());
    }
}

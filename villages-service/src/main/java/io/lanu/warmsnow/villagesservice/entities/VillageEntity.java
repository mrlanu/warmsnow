package io.lanu.warmsnow.villagesservice.entities;

import io.lanu.warmsnow.common_models.FieldType;
import io.lanu.warmsnow.common_models.VillageType;
import io.lanu.warmsnow.common_models.models.Army;
import io.lanu.warmsnow.common_models.models.Field;
import io.lanu.warmsnow.common_models.models.ProducePerHour;
import io.lanu.warmsnow.common_models.models.Warehouse;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document("villages")
@TypeAlias("village")
@Data
@NoArgsConstructor
public class VillageEntity {
    @Id
    private String villageId;
    private String accountId;
    private Integer x;
    private Integer y;
    private Integer culture;
    private Integer population;
    private VillageType villageType;
    private Warehouse warehouse;
    private Army army;
    private ProducePerHour producePerHour;
    private List<Field> fields;
    @LastModifiedDate
    private LocalDateTime modified;

    public void addToProducePerHour(FieldType fieldType, int amount){
        Integer previous = producePerHour.getGoods().get(fieldType);
        producePerHour.getGoods().put(fieldType, previous + amount);
    }
}

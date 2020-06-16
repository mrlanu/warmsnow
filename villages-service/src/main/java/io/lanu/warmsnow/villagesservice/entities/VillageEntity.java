package io.lanu.warmsnow.villagesservice.entities;

import io.lanu.warmsnow.common_models.VillageType;
import io.lanu.warmsnow.common_models.models.Field;
import io.lanu.warmsnow.common_models.models.ProducePerHour;
import io.lanu.warmsnow.common_models.models.Warehouse;
import io.lanu.warmsnow.common_models.models.FieldTaskModel;
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
    private ProducePerHour producePerHour;
    private List<Field> fields;
    private List<FieldTaskModel> tasks;
    @LastModifiedDate
    private LocalDateTime modified;

    public VillageEntity(String accountId, Integer x, Integer y, Integer culture, Integer population,
                         VillageType villageType, Warehouse warehouse, ProducePerHour producePerHour, List<Field> fields) {
        this.accountId = accountId;
        this.x = x;
        this.y = y;
        this.culture = culture;
        this.population = population;
        this.villageType = villageType;
        this.warehouse = warehouse;
        this.producePerHour = producePerHour;
        this.fields = fields;
    }
}

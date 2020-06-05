package io.lanu.warmsnow.villagesservice.entities;

import io.lanu.warmsnow.common_models.VillageType;
import io.lanu.warmsnow.templates.templates_client.models.Field;
import io.lanu.warmsnow.templates.templates_client.models.Warehouse;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

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
    private io.lanu.warmsnow.templates.templates_client.models.Warehouse warehouse;
    private List<Field> fields;

    public VillageEntity(String accountId, Integer x, Integer y, Integer culture, Integer population,
                         VillageType villageType, Warehouse warehouse, List<Field> fields) {
        this.accountId = accountId;
        this.x = x;
        this.y = y;
        this.culture = culture;
        this.population = population;
        this.villageType = villageType;
        this.warehouse = warehouse;
        this.fields = fields;
    }
}

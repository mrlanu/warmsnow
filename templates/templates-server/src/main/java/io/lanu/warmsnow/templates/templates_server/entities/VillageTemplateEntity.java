package io.lanu.warmsnow.templates.templates_server.entities;

import io.lanu.warmsnow.common_models.VillageType;
import io.lanu.warmsnow.common_models.models.Army;
import io.lanu.warmsnow.common_models.models.Field;
import io.lanu.warmsnow.common_models.models.ProducePerHour;
import io.lanu.warmsnow.common_models.models.Warehouse;
import io.lanu.warmsnow.common_models.models.buildings.BuildingBase;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document("villages-templates")
@TypeAlias("villages-templates")
@NoArgsConstructor
public class VillageTemplateEntity {
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
    private List<BuildingBase> buildings;
    // private List<Building> buildings;


    public VillageTemplateEntity(VillageType villageType, List<Field> fields, List<BuildingBase> buildings) {
        this.villageType = villageType;
        this.warehouse = new Warehouse();
        this.army = new Army();
        this.producePerHour = new ProducePerHour();
        this.fields = fields;
        this.buildings = buildings;
        this.x = 0;
        this.y = 0;
        this.population = 10;
        this.culture =100;
    }
}

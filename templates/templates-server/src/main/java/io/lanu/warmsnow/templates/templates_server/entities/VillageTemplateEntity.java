package io.lanu.warmsnow.templates.templates_server.entities;

import io.lanu.warmsnow.templates.templates_client.models.Field;
import io.lanu.warmsnow.templates.templates_client.dto.VillageType;
import io.lanu.warmsnow.templates.templates_client.models.Warehouse;
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
    private List<Field> fields;
    // private List<Building> buildings;


    public VillageTemplateEntity(VillageType villageType, List<Field> fields) {
        this.villageType = villageType;
        this.warehouse = new Warehouse();
        this.fields = fields;
        this.x = 0;
        this.y = 0;
        this.population = 10;
        this.culture =100;
    }
}
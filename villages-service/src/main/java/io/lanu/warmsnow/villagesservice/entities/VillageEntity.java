package io.lanu.warmsnow.villagesservice.entities;

import io.lanu.warmsnow.villagesservice.models.BuildingModel;
import io.lanu.warmsnow.villagesservice.models.Warehouse;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document("villages")
@TypeAlias("village")
@Data
public class VillageEntity {
    @Id
    private String villageId;
    private String accountId;
    private String name;
    private Warehouse warehouse;
    //@DBRef
    private List<BuildingModel> productionBuildings;

    @PersistenceConstructor
    public VillageEntity(String accountId) {
        this.accountId = accountId;
        this.name = "New village";
        this.warehouse = new Warehouse(0, 0, 0);
        this.productionBuildings = new ArrayList<>();
    }

    public void addBuilding(BuildingModel prodBuilding){
        productionBuildings.add(prodBuilding);
    }
}

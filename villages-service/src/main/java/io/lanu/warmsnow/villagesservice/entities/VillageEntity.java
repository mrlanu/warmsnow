package io.lanu.warmsnow.villagesservice.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Document("villages")
@TypeAlias("village")
@Data
public class VillageEntity {
    @Id
    private String villageId;
    private String accountId;
    private String name;
    //@DBRef
    private List<BuildingEntity> productionBuildings;

    @PersistenceConstructor
    public VillageEntity(String accountId) {
        this.villageId = UUID.randomUUID().toString();
        this.accountId = accountId;
        this.name = "New village";
        this.productionBuildings = new ArrayList<>();
    }

    public void addBuilding(BuildingEntity prodBuilding){
        productionBuildings.add(prodBuilding);
    }
}

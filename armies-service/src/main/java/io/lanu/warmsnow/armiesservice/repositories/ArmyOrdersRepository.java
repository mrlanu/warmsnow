package io.lanu.warmsnow.armiesservice.repositories;

import io.lanu.warmsnow.armiesservice.entities.ArmyOrderEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ArmyOrdersRepository extends MongoRepository<ArmyOrderEntity, String> {
    List<ArmyOrderEntity> findAllByVillageId(String villageId);
}

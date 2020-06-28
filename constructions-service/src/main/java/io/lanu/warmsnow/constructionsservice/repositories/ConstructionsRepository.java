package io.lanu.warmsnow.constructionsservice.repositories;

import io.lanu.warmsnow.constructionsservice.entities.FieldTaskEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ConstructionsRepository extends MongoRepository<FieldTaskEntity, String> {
    List<FieldTaskEntity> findAllByVillageId(String villageId);
}

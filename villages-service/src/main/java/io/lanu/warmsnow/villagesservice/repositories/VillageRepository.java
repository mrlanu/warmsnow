package io.lanu.warmsnow.villagesservice.repositories;

import io.lanu.warmsnow.villagesservice.entities.VillageEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface VillageRepository extends MongoRepository<VillageEntity, String> {
    List<VillageEntity> findAllByAccountId(String accountId);
}

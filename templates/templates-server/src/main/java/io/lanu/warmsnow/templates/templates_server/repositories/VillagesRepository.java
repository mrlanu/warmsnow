package io.lanu.warmsnow.templates.templates_server.repositories;

import io.lanu.warmsnow.common_models.VillageType;
import io.lanu.warmsnow.templates.templates_server.entities.VillageTemplateEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VillagesRepository extends MongoRepository<VillageTemplateEntity, String> {
    VillageTemplateEntity findByVillageType(VillageType villageType);
}

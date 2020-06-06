package io.lanu.warmsnow.templates.templates_server.services;

import io.lanu.warmsnow.common_models.VillageType;
import io.lanu.warmsnow.templates.templates_server.entities.VillageTemplateEntity;

import java.util.List;

public interface VillagesService {

    List<VillageTemplateEntity> findAll();
    VillageTemplateEntity save(VillageTemplateEntity village);
    VillageTemplateEntity findByVillageType(VillageType villageType);
}

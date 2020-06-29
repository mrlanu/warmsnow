package io.lanu.warmsnow.villagesservice.services;

import io.lanu.warmsnow.templates.templates_client.dto.VillageDto;

public interface VillageViewBuilder {
    VillageDto build(String villageId);
}

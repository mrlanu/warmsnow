package io.lanu.warmsnow.templates.templates_server.services;

import io.lanu.warmsnow.templates.templates_client.dto.VillageType;
import io.lanu.warmsnow.templates.templates_server.entities.VillageTemplateEntity;
import io.lanu.warmsnow.templates.templates_server.repositories.VillagesRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VillagesServiceImpl implements VillagesService {

    private final VillagesRepository villagesRepository;

    public VillagesServiceImpl(VillagesRepository villagesRepository) {
        this.villagesRepository = villagesRepository;
    }

    @Override
    public List<VillageTemplateEntity> findAll() {
        return villagesRepository.findAll();
    }

    @Override
    public VillageTemplateEntity save(VillageTemplateEntity village) {
        return villagesRepository.save(village);
    }

    @Override
    public VillageTemplateEntity findByVillageType(VillageType villageType) {
        return villagesRepository.findByVillageType(villageType);
    }
}

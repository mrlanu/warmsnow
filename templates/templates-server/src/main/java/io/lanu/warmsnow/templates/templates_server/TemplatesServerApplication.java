package io.lanu.warmsnow.templates.templates_server;

import io.lanu.warmsnow.common_models.FieldType;
import io.lanu.warmsnow.common_models.NationsType;
import io.lanu.warmsnow.common_models.UnitType;
import io.lanu.warmsnow.common_models.VillageType;
import io.lanu.warmsnow.common_models.models.Field;
import io.lanu.warmsnow.templates.templates_server.entities.FieldTemplateEntity;
import io.lanu.warmsnow.templates.templates_server.entities.UnitTemplateEntity;
import io.lanu.warmsnow.templates.templates_server.entities.VillageTemplateEntity;
import io.lanu.warmsnow.templates.templates_server.services.BuildingsService;
import io.lanu.warmsnow.templates.templates_server.services.FieldsService;
import io.lanu.warmsnow.templates.templates_server.services.UnitsService;
import io.lanu.warmsnow.templates.templates_server.services.VillagesService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.util.*;

@SpringBootApplication
@EnableDiscoveryClient
public class TemplatesServerApplication {

    private final BuildingsService buildingsService;
    private final VillagesService villagesService;
    private final FieldsService fieldsService;
    private final UnitsService unitsService;

    public TemplatesServerApplication(BuildingsService buildingsService,
                                      VillagesService villagesService, FieldsService fieldsService, UnitsService unitsService) {
        this.buildingsService = buildingsService;
        this.villagesService = villagesService;
        this.fieldsService = fieldsService;
        this.unitsService = unitsService;
    }

    public static void main(String[] args) {
        SpringApplication.run(TemplatesServerApplication.class, args);
    }

    /*@Bean
    public CommandLineRunner createSomeBuildingsTemplates() {
        return args -> {
            if (buildingsService.findAll().size() == 0){
                Map<Integer, Integer> produces = new HashMap<>();
                Map<String, Integer> constCost = new HashMap<>();
                produces.put(5, 7);
                produces.put(15, 14);
                produces.put(1, 34);
                produces.put(4, 56);
                produces.put(8, 84);
                produces.put(24, 169);
                constCost.put("coins", 96);
                BuildingEntity hunter = new BuildingEntity(null,
                        "hunter", 28, constCost, 10000, produces, false);
                BuildingEntity blacksmith = new BuildingEntity(null,
                        "blacksmith", 12, constCost, 30000, produces, false);
                buildingsService.save(hunter);
                buildingsService.save(blacksmith);
            }
        };
    }*/

    @Bean
    public CommandLineRunner createSomeFieldsTemplates() {
        return args -> {
            if (fieldsService.findAll().size() == 0){
                List<FieldTemplateEntity> fields = Arrays.asList(
                        new FieldTemplateEntity(0, 2, FieldType.WOOD, 75,
                                false, false, 60, getResourcesToNextLevel(150)),
                        new FieldTemplateEntity(0, 2, FieldType.CLAY, 75,
                                false, false, 60, getResourcesToNextLevel(150)),
                        new FieldTemplateEntity(0, 2, FieldType.IRON, 75,
                                false, false, 60, getResourcesToNextLevel(150)),
                        new FieldTemplateEntity(0, 2, FieldType.CROP, 75,
                                false, false, 60, getResourcesToNextLevel(150)),
                        new FieldTemplateEntity(0, 3, FieldType.WOOD, 150,
                                false, false, 180, getResourcesToNextLevel(550)),
                        new FieldTemplateEntity(0, 3, FieldType.CLAY, 150,
                                false, false, 180, getResourcesToNextLevel(550)),
                        new FieldTemplateEntity(0, 3, FieldType.IRON, 150,
                                false, false, 180, getResourcesToNextLevel(550)),
                        new FieldTemplateEntity(0, 3, FieldType.CROP, 150,
                                false, false, 180, getResourcesToNextLevel(550))
                );
                fieldsService.saveAll(fields);
            }
        };
    }

    @Bean
    public CommandLineRunner createSomeVillagesTemplates() {
        return args -> {
            if (villagesService.findAll().size() == 0){
                List<Field> fields = Arrays.asList(
                        new Field(0, 1, FieldType.WOOD, 10,
                                false, false, 30, getResourcesToNextLevel(50)),
                        new Field(1, 1, FieldType.CLAY, 10,
                                false, false, 30, getResourcesToNextLevel(50)),
                        new Field(2, 1, FieldType.IRON, 10,
                                false, false, 30, getResourcesToNextLevel(50)),
                        new Field(3, 1, FieldType.CROP, 10,
                                false, false, 30, getResourcesToNextLevel(50))
                );
                villagesService.save(new VillageTemplateEntity(VillageType.SIX, fields));
            }
        };
    }

    @Bean
    public CommandLineRunner createSomeUnitsTemplates() {
        return args -> {
            if (unitsService.findAll().size() == 0){
                Map<FieldType, Integer> resources = Map.of(
                        FieldType.WOOD, 120,
                        FieldType.CLAY, 100,
                        FieldType.IRON, 150,
                        FieldType.CROP, 30);

                unitsService.save(new UnitTemplateEntity(UnitType.LEGIONNAIRE, NationsType.ROME, resources,
                        40, 35, 60, 6, 50, 1, 1166));
            }
        };
    }

    private Map<FieldType, BigDecimal> getResourcesToNextLevel(int resourceAmount){
        return  Map.of(
                FieldType.WOOD, BigDecimal.valueOf(resourceAmount),
                FieldType.CLAY, BigDecimal.valueOf(resourceAmount),
                FieldType.IRON, BigDecimal.valueOf(resourceAmount),
                FieldType.CROP, BigDecimal.valueOf(resourceAmount));
    }


}

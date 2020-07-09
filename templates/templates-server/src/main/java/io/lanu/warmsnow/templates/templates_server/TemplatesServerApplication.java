package io.lanu.warmsnow.templates.templates_server;

import io.lanu.warmsnow.common_models.FieldType;
import io.lanu.warmsnow.common_models.NationsType;
import io.lanu.warmsnow.common_models.UnitType;
import io.lanu.warmsnow.common_models.VillageType;
import io.lanu.warmsnow.common_models.models.Field;
import io.lanu.warmsnow.common_models.models.buildings.*;
import io.lanu.warmsnow.templates.templates_server.entities.FieldTemplateEntity;
import io.lanu.warmsnow.templates.templates_server.entities.UnitTemplateEntity;
import io.lanu.warmsnow.templates.templates_server.entities.VillageTemplateEntity;
import io.lanu.warmsnow.templates.templates_server.entities.buildings.BarrackTemplateEntity;
import io.lanu.warmsnow.templates.templates_server.entities.buildings.GranaryTemplateEntity;
import io.lanu.warmsnow.templates.templates_server.entities.buildings.WarehouseBuildingTemplateEntity;
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

    private final VillagesService villagesService;
    private final FieldsService fieldsService;
    private final UnitsService unitsService;
    private final BuildingsService buildingsService;

    public TemplatesServerApplication(VillagesService villagesService, FieldsService fieldsService,
                                      UnitsService unitsService, BuildingsService buildingsService) {
        this.villagesService = villagesService;
        this.fieldsService = fieldsService;
        this.unitsService = unitsService;
        this.buildingsService = buildingsService;
    }

    public static void main(String[] args) {
        SpringApplication.run(TemplatesServerApplication.class, args);
    }

    @Bean
    public CommandLineRunner createSomeBuildingsTemplates() {
        return args -> {
            if (buildingsService.getAllBuildings().size() == 0){
                Map<FieldType, Integer> toNextLevel = Map.of(
                        FieldType.WOOD, 200,
                        FieldType.CLAY, 200,
                        FieldType.IRON, 200,
                        FieldType.CROP, 200);
                WarehouseBuildingTemplateEntity warehouse =
                        new WarehouseBuildingTemplateEntity(1, 0, toNextLevel, 60, 750);
                BarrackTemplateEntity barrack =
                        new BarrackTemplateEntity(1, 0, toNextLevel, 60, 1);
                GranaryTemplateEntity granary =
                        new GranaryTemplateEntity(1, 0, toNextLevel, 60, 750);
                buildingsService.saveAll(Arrays.asList(warehouse, barrack, granary));
            }
        };
    }

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
                List<BuildingBase> buildings = Arrays.asList(
                        new EmptySpot(0, 0, null, 0),
                        new MainBuilding(1, 1, Map.of(FieldType.WOOD, 200, FieldType.CLAY, 200,
                                FieldType.IRON, 200, FieldType.CROP, 200), 60, 2),
                        new EmptySpot(0, 2, null, 0),
                        new EmptySpot(0, 3, null, 0)
                );
                villagesService.save(new VillageTemplateEntity(VillageType.SIX, fields, buildings));
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

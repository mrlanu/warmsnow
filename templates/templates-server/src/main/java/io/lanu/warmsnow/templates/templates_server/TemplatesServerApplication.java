package io.lanu.warmsnow.templates.templates_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class TemplatesServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TemplatesServerApplication.class, args);
    }

    /*@Bean
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
                        new MainBuilding(1, 1, Map.of(FieldType.WOOD, BigDecimal.valueOf(200), FieldType.CLAY,
                                BigDecimal.valueOf(200), FieldType.IRON, BigDecimal.valueOf(200),
                                FieldType.CROP, BigDecimal.valueOf(200)), 60, 2),
                        new EmptySpot(0, 2, null, 0),
                        new EmptySpot(0, 3, null, 0)
                );
                villagesService.save(new VillageTemplateEntity(VillageType.SIX, fields, buildings));
            }
        };
    }*/

    /*private Map<FieldType, BigDecimal> getResourcesToNextLevel(int resourceAmount){
        return  Map.of(
                FieldType.WOOD, BigDecimal.valueOf(resourceAmount),
                FieldType.CLAY, BigDecimal.valueOf(resourceAmount),
                FieldType.IRON, BigDecimal.valueOf(resourceAmount),
                FieldType.CROP, BigDecimal.valueOf(resourceAmount));
    }*/

}

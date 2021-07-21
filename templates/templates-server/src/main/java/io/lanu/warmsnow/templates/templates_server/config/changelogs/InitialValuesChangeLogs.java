package io.lanu.warmsnow.templates.templates_server.config.changelogs;

import com.github.mongobee.changeset.ChangeLog;
import com.github.mongobee.changeset.ChangeSet;
import io.lanu.warmsnow.common_models.FieldType;
import io.lanu.warmsnow.common_models.NationsType;
import io.lanu.warmsnow.common_models.UnitType;
import io.lanu.warmsnow.common_models.VillageType;
import io.lanu.warmsnow.common_models.models.Field;
import io.lanu.warmsnow.common_models.models.buildings.BuildingBase;
import io.lanu.warmsnow.common_models.models.buildings.EmptySpot;
import io.lanu.warmsnow.common_models.models.buildings.MainBuilding;
import io.lanu.warmsnow.templates.templates_server.entities.FieldTemplateEntity;
import io.lanu.warmsnow.templates.templates_server.entities.UnitTemplateEntity;
import io.lanu.warmsnow.templates.templates_server.entities.VillageTemplateEntity;
import io.lanu.warmsnow.templates.templates_server.entities.buildings.BarrackTemplateEntity;
import io.lanu.warmsnow.templates.templates_server.entities.buildings.GranaryTemplateEntity;
import io.lanu.warmsnow.templates.templates_server.entities.buildings.WarehouseBuildingTemplateEntity;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


@ChangeLog
public class InitialValuesChangeLogs {

    @ChangeSet(order = "001", id = "insertVillage", author = "mrLanu")
    public void insertVillage(MongoTemplate mongoTemplate) {
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

        mongoTemplate.save(new VillageTemplateEntity(VillageType.SIX, fields, buildings));
    }

    @ChangeSet(order = "002", id = "insertBuildings", author = "mrLanu")
    public void insertBuildings(MongoTemplate mongoTemplate) {
        Map<FieldType, BigDecimal> toNextLevel = Map.of(
                FieldType.WOOD, BigDecimal.valueOf(200),
                FieldType.CLAY, BigDecimal.valueOf(200),
                FieldType.IRON, BigDecimal.valueOf(200),
                FieldType.CROP, BigDecimal.valueOf(200));
        WarehouseBuildingTemplateEntity warehouse =
                new WarehouseBuildingTemplateEntity(1, 0, toNextLevel, 60, 750);
        BarrackTemplateEntity barrack =
                new BarrackTemplateEntity(1, 0, toNextLevel, 60, 1);
        GranaryTemplateEntity granary =
                new GranaryTemplateEntity(1, 0, toNextLevel, 60, 750);

        Arrays.asList(warehouse, barrack, granary).forEach(mongoTemplate::save);
    }

    @ChangeSet(order = "003", id = "insertUnits", author = "mrLanu")
    public void insertUnits(MongoTemplate mongoTemplate) {
        Map<FieldType, Integer> resources = Map.of(
                FieldType.WOOD, 120,
                FieldType.CLAY, 100,
                FieldType.IRON, 150,
                FieldType.CROP, 30);

        mongoTemplate.save(new UnitTemplateEntity(UnitType.LEGIONNAIRE, NationsType.ROME, resources,
                40, 35, 60, 6, 50, 1, 1166));
    }


    @ChangeSet(order = "004", id = "insertFields", author = "mrLanu")
    public void insertFields(MongoTemplate mongoTemplate) {
        Arrays.asList(
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
        ).forEach(mongoTemplate::save);
    }

    private Map<FieldType, BigDecimal> getResourcesToNextLevel(int resourceAmount){
        return  Map.of(
                FieldType.WOOD, BigDecimal.valueOf(resourceAmount),
                FieldType.CLAY, BigDecimal.valueOf(resourceAmount),
                FieldType.IRON, BigDecimal.valueOf(resourceAmount),
                FieldType.CROP, BigDecimal.valueOf(resourceAmount));
    }
}

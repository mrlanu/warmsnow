package io.lanu.warmsnow.armiesservice.entities;

import io.lanu.warmsnow.common_models.UnitType;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document("army-orders")
@TypeAlias("army-order")
@NoArgsConstructor
public class ArmyOrderEntity {
    @Id
    private String id;
    @CreatedDate
    private LocalDateTime created;
    private String villageId;
    private LocalDateTime lastTime;
    private UnitType unitType;
    private Integer leftTrain;
    private Long durationEach;
    private LocalDateTime endOrderTime;

    public ArmyOrderEntity(String villageId, LocalDateTime lastTime, UnitType unitType,
                           Integer leftTrain, Long durationEach, LocalDateTime endOrderTime) {
        this.villageId = villageId;
        this.lastTime = lastTime;
        this.unitType = unitType;
        this.leftTrain = leftTrain;
        this.durationEach = durationEach;
        this.endOrderTime = endOrderTime;
    }
}

package io.lanu.warmsnow.common_models.models.buildings.requirements;

import io.lanu.warmsnow.common_models.models.buildings.MainBuilding;

import java.util.Collections;

public class BarrackReq extends RequiredBase {
    public BarrackReq() {
        super();
        this.needBuildings = Collections.singletonList(
                new MainBuilding(3, 0, null, 0, 0)
        );
    }
}

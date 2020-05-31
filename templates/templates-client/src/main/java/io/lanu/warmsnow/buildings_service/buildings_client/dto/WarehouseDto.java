package io.lanu.warmsnow.buildings_service.buildings_client.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseDto {
    private Integer coins;
    private Integer foods;
    private Integer culture;
}

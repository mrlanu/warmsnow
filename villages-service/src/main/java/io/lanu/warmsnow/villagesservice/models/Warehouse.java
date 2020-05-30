package io.lanu.warmsnow.villagesservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Warehouse {
    private Integer coins;
    private Integer foods;
    private Integer culture;
}

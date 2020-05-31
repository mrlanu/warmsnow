package io.lanu.warmsnow.templates.templates_client.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseDto {
    private BigDecimal wood;
    private BigDecimal clay;
    private BigDecimal iron;
    private BigDecimal crop;
}

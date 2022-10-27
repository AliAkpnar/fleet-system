package com.aa.fleet.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ParkResponseDto {
    private int floor;
    private String plate;
    private BigDecimal pricePerMinute;
    private BigDecimal totalPrice;
}

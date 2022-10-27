package com.aa.fleet.model.dto;

import com.aa.fleet.enums.VehicleType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ParkRequestDto {
    private Double height;
    private Double weight;
    private String plate;
    private VehicleType vehicleType;
}

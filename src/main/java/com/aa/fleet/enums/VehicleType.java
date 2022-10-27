package com.aa.fleet.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;
@Getter
@AllArgsConstructor
public enum VehicleType {
    CAR(1),
    TRUCK(2);

    private int vehicleId;

    public static VehicleType decode(int vehicleId) {
        return Stream.of(VehicleType.values())
                .filter(e -> e.vehicleId == vehicleId)
                .findFirst()
                .orElse(null);
    }
}


package com.aa.fleet.exception;

import lombok.Getter;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Getter
public class VehicleNotFoundException extends ParkingSystemRuntimeException{

    public VehicleNotFoundException() {
        super(NOT_FOUND, "Vehicle Not Found");
    }
}

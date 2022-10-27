package com.aa.fleet.exception;

import lombok.Getter;

import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Getter
public class PriceCanNotCalculateException extends ParkingSystemRuntimeException{

    public PriceCanNotCalculateException() {
        super(NOT_ACCEPTABLE, "Price Can Not Calculate");
    }
}

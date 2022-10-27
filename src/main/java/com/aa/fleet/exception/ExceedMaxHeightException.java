package com.aa.fleet.exception;

import lombok.Getter;

import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Getter
public class ExceedMaxHeightException extends ParkingSystemRuntimeException{

    public ExceedMaxHeightException() {
        super(NOT_ACCEPTABLE, "Exceeds Maximum Height");
    }
}

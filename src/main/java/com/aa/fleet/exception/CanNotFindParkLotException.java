package com.aa.fleet.exception;

import lombok.Getter;

import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Getter
public class CanNotFindParkLotException extends ParkingSystemRuntimeException{

    public CanNotFindParkLotException() {
        super(NOT_FOUND, "Can not find Parking Lot");
    }
}

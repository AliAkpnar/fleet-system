package com.aa.fleet.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Getter
public class ParkingSystemRuntimeException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final HttpStatus status;

    public ParkingSystemRuntimeException(int code, String message) {
        this(BAD_REQUEST, message);
    }

    public ParkingSystemRuntimeException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }
}

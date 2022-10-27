package com.aa.fleet.service;

import com.aa.fleet.model.dto.ParkRequestDto;
import com.aa.fleet.model.dto.ParkResponseDto;

import java.util.List;

public interface ParkService {
    List<ParkResponseDto> getParkedVehicleInformation();
    ParkResponseDto parkVehicle(ParkRequestDto parkRequestDto);
    ParkResponseDto getParkedVehicleByPlate(String plate);
    ParkResponseDto exitVehicle(String plate);
}

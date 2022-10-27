package com.aa.fleet.controller;

import com.aa.fleet.model.dto.ParkRequestDto;
import com.aa.fleet.model.dto.ParkResponseDto;
import com.aa.fleet.service.ParkService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "Park Controller")
public class ParkController {

    private final ParkService parkService;

    @GetMapping("/parks")
    List<ParkResponseDto> getAllParks(){
        log.info("Park Controller -> getAllParks...");
        return parkService.getParkedVehicleInformation();
    }

    @PostMapping("/parks")
    ParkResponseDto parkVehicle(@RequestBody ParkRequestDto parkRequestDto){
        log.info("Park Controller -> parkVehicle...");
        return parkService.parkVehicle(parkRequestDto);
    }

    @GetMapping("/parks/{plate}")
    ParkResponseDto getByPlate(@PathVariable String plate){
        log.info("Park Controller -> getByPlate and plate : {}", plate);
        return parkService.getParkedVehicleByPlate(plate);
    }

    @DeleteMapping("/parks/{plate}")
    ParkResponseDto exitPark(@PathVariable String plate){
        log.info("Park Controller -> exitPark and plate : {}", plate);
        return parkService.exitVehicle(plate);
    }

}

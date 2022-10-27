package com.aa.fleet.service.impl;

import com.aa.fleet.exception.CanNotFindParkLotException;
import com.aa.fleet.exception.ExceedMaxHeightException;
import com.aa.fleet.exception.VehicleNotFoundException;
import com.aa.fleet.model.dto.ParkRequestDto;
import com.aa.fleet.model.dto.ParkResponseDto;
import com.aa.fleet.persistence.entity.ParkEntity;
import com.aa.fleet.persistence.entity.VehicleInfoEntity;
import com.aa.fleet.persistence.repository.ParkRepository;
import com.aa.fleet.persistence.repository.VehicleRepository;
import com.aa.fleet.service.ParkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.aa.fleet.model.converter.VehicleConverter.convertToVehicleInfoEntity;
import static com.aa.fleet.model.converter.VehicleConverter.entityToParkResponseDto;
import static com.aa.fleet.util.ParkCalculator.calculateParkingPrice;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ParkServiceImpl implements ParkService {
    public static final double MAX_HEIGHT = 100.0;

    private final VehicleRepository vehicleRepository;
    private final ParkRepository parkRepository;

    @Override
    public List<ParkResponseDto> getParkedVehicleInformation() {
        return Optional.ofNullable(vehicleRepository.findAll())
                .stream()
                .flatMap(List::stream)
                .map(e -> entityToParkResponseDto(e, null))
                .collect(Collectors.toList());
    }

    @Override
    public ParkResponseDto parkVehicle(ParkRequestDto parkRequestDto) {
        if(parkRequestDto.getHeight() > MAX_HEIGHT)
            throw new ExceedMaxHeightException();
        return parkVehicleWithGivenValues(parkRequestDto);
    }

    @Override
    public ParkResponseDto getParkedVehicleByPlate(String plate) {
        log.info("ParkServiceImpl -> getParkedVehicleByPlate with the value: {}", plate);
        return vehicleRepository.findByPlate(plate)
                .map(e -> entityToParkResponseDto(e, calculateParkingPrice(e.getParkEntity())))
                .orElseThrow(VehicleNotFoundException::new);
    }

    @Override
    public ParkResponseDto exitVehicle(String plate) {
        log.info("ParkServiceImpl -> exitVehicle with the value: {}", plate);
        return vehicleRepository.findByPlate(plate)
                .map(e -> exitParkAndUpdateRemainingWeight(e))
                .map(e -> entityToParkResponseDto(e, calculateParkingPrice(e.getParkEntity())))
                .orElseThrow(VehicleNotFoundException::new);
    }

    private ParkResponseDto parkVehicleWithGivenValues(ParkRequestDto parkRequestDto){
        return Optional.ofNullable(parkRepository.findAll())
                .stream()
                .flatMap(List::stream)
                .filter(e -> e.getRemainingWeight() >= parkRequestDto.getWeight())
                .filter(e -> e.getRemainingWeight() < e.getTotalWeight())
                .findFirst()
                .map(e -> parkRepository.save(updateRemainingWeight(e, parkRequestDto)))
                .map(e -> vehicleRepository.save(convertToVehicleInfoEntity(e, parkRequestDto)))
                .map(e -> entityToParkResponseDto(e, null))
                .orElseThrow(CanNotFindParkLotException::new);
    }

    private ParkEntity updateRemainingWeight(ParkEntity entity, ParkRequestDto dto){
        entity.setRemainingWeight(entity.getRemainingWeight() - dto.getWeight());
        log.info("ParkServiceImpl -> updateRemainingWeight remainingWeight is: {}",
                entity.getRemainingWeight() - dto.getWeight());
        return entity;
    }

    private  VehicleInfoEntity exitParkAndUpdateRemainingWeight(VehicleInfoEntity entity){
        log.info("ParkServiceImpl -> exitParkAndUpdateRemainingWeight deleted entity id {}",
                entity.getId());
        vehicleRepository.delete(entity);
        entity.getParkEntity().setRemainingWeight(
                entity.getParkEntity().getRemainingWeight() + entity.getWeight());
        return entity;
    }

}

package com.aa.fleet.service.impl;

import com.aa.fleet.enums.VehicleType;
import com.aa.fleet.model.dto.ParkRequestDto;
import com.aa.fleet.model.dto.ParkResponseDto;
import com.aa.fleet.persistence.entity.ParkEntity;
import com.aa.fleet.persistence.entity.VehicleInfoEntity;
import com.aa.fleet.persistence.repository.ParkRepository;
import com.aa.fleet.persistence.repository.VehicleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class ParkServiceImplTest {

    @Mock
    private VehicleRepository vehicleRepository;

    @Mock
    private ParkRepository parkRepository;

    @InjectMocks
    private ParkServiceImpl parkService;

    @Test
    void should_return_expected_entity_values_when_get_method_called() {
        //Given
        int expectedSize = 1;
        int expectedFloor = generateVehicleInfoEntity().getFloor();
        String expectedPlate = generateVehicleInfoEntity().getPlate();

        //When
        when(vehicleRepository.findAll())
                .thenReturn(Arrays.asList(generateVehicleInfoEntity()));

        List<ParkResponseDto> actual = parkService.getParkedVehicleInformation();
        //Then
        assertAll("Get All Vehicles",
                () -> assertEquals(expectedSize, actual.size()),
                () -> assertEquals(expectedFloor, actual.get(0).getFloor()),
                () -> assertEquals(expectedPlate, actual.get(0).getPlate()));
    }

    @Test
    void should_thrown_exception_when_exceeds_height_limit() {
        //Given
        String expectedMessage = "Exceeds Maximum Height";
        ParkRequestDto expectedReq = generatRequestDto();
        expectedReq.setHeight(101D);

        //When
        Exception actualThrown = assertThrows(
                Exception.class,
                () -> parkService.parkVehicle(expectedReq)
        );
        //Then
        assertAll("Exceeded Limit Exception Control",
                () -> assertNotNull(actualThrown.getMessage()),
                () -> assertEquals(expectedMessage, actualThrown.getMessage()));
    }

    @Test
    void should_thrown_exception_when_there_is_no_lot_for_parking(){

        //Given
        ParkEntity parkEntity = generateParkEntity();
        parkEntity.setRemainingWeight(100D);
        parkEntity.setTotalWeight(100D);
        String expectedMessage = "Can not find Parking Lot";

        //When
        when(parkRepository.findAll())
                .thenReturn(Arrays.asList(parkEntity));

        Exception actualThrown = assertThrows(
                Exception.class,
                () -> parkService.parkVehicle(generatRequestDto())
        );

        //Then
        assertAll("Can not find Parking Lot Control",
                () -> assertNotNull(actualThrown.getMessage()),
                () -> assertEquals(expectedMessage, actualThrown.getMessage()));
    }

    @Test
    void should_park_vehicle_when_there_is_suitable_lot(){

        //Given
        ParkEntity parkEntity = generateParkEntity();
        parkEntity.setRemainingWeight(80D);
        parkEntity.setTotalWeight(100D);

        int expectedFloor = 1;
        String expectedPlate = "32tr35";
        BigDecimal expectedPricePerMinute = BigDecimal.valueOf(2);

        //When
        when(parkRepository.findAll())
                .thenReturn(Arrays.asList(parkEntity));
        when(parkRepository.save(any(ParkEntity.class)))
                .thenAnswer(i -> i.getArguments()[0]);
        when(vehicleRepository.save(any(VehicleInfoEntity.class)))
                .thenAnswer(i -> i.getArguments()[0]);

        ParkResponseDto actual = parkService.parkVehicle(generatRequestDto());

        //Then
        assertAll("Park Response Control",
                () -> assertNotNull(actual),
                () -> assertEquals(expectedFloor, actual.getFloor()),
                () -> assertEquals(expectedPlate, actual.getPlate()),
                () -> assertEquals(expectedPricePerMinute, actual.getPricePerMinute()));
    }

    @Test
    void should_return_vehicle_info_when_plate_send() {
        //Given
        String expectedPlate = "32tr35";
        int expectedFloor = 1;
        BigDecimal expectedPricePerMinute = BigDecimal.valueOf(2);

        //When
        when(vehicleRepository.findByPlate(expectedPlate))
                .thenReturn(Optional.of(generateVehicleInfoEntity()));

        ParkResponseDto actual = parkService.getParkedVehicleByPlate(expectedPlate);

        //Then
        assertAll("Park Response Control",
                () -> assertNotNull(actual),
                () -> assertEquals(expectedFloor, actual.getFloor()),
                () -> assertEquals(expectedPlate, actual.getPlate()),
                () -> assertEquals(expectedPricePerMinute, actual.getPricePerMinute()));
    }

    @Test
    void when_vehicle_exits_total_price_must_be_calculated() {
        //Given
        VehicleInfoEntity entity = generateVehicleInfoEntity();
        entity.getParkEntity().setLastModifiedDate(LocalDateTime.now().minusMinutes(15));

        BigDecimal expectedTotalPrice = BigDecimal.valueOf(30);
        //When
        when(vehicleRepository.findByPlate(entity.getPlate()))
                .thenReturn(Optional.of(entity));
        ParkResponseDto actual = parkService.exitVehicle(entity.getPlate());

        //Then
        assertAll("Total Price Control",
                () -> assertNotNull(actual.getTotalPrice()),
                () -> assertEquals(expectedTotalPrice, actual.getTotalPrice()));
    }

    private ParkEntity generateParkEntity(){
        return ParkEntity.builder()
                .remainingWeight(5D)
                .totalWeight(100D)
                .pricePerMinute(BigDecimal.valueOf(2))
                .floor(1)
                .id(1L)
                .createdDate(LocalDateTime.now())
                .parkList(new LinkedList<>())
                .lastModifiedDate(LocalDateTime.now())
                .build();
    }

    private VehicleInfoEntity generateVehicleInfoEntity(){
        return VehicleInfoEntity.builder()
                .weight(10D)
                .height(20D)
                .parkEntity(generateParkEntity())
                .type(2)
                .plate("32tr35")
                .floor(1)
                .id(1L)
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();
    }

    private ParkRequestDto generatRequestDto(){
        return ParkRequestDto.builder()
                .vehicleType(VehicleType.TRUCK)
                .plate("32tr35")
                .height(10D)
                .weight(20D)
                .build();
    }


    private ParkResponseDto generateResponseDto(){
        return ParkResponseDto.builder()
                .totalPrice(BigDecimal.valueOf(1))
                .pricePerMinute(BigDecimal.valueOf(2))
                .floor(1)
                .plate("32tr35")
                .build();
    }

}
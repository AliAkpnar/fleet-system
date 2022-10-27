package com.aa.fleet.util;

import com.aa.fleet.persistence.entity.ParkEntity;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ParkCalculatorTest {

    @Test
    void should_thrown_exception_last_modified_date_after_from_now(){

        //Given
        ParkEntity parkEntity = new ParkEntity();
        parkEntity.setLastModifiedDate(LocalDateTime.now().plusMinutes(10));
        String expectedMessage = "Price Can Not Calculate";

        //When
        Exception actualThrown = assertThrows(
                Exception.class,
                () -> ParkCalculator.calculateParkingPrice(parkEntity)
        );

        //Then
        assertAll("Price Can Not Calculate",
                () -> assertNotNull(actualThrown.getMessage()),
                () -> assertEquals(expectedMessage, actualThrown.getMessage()));
    }


    @Test
    void should_calculate_parking_price(){
        //Given
        ParkEntity parkEntity = new ParkEntity();
        parkEntity.setLastModifiedDate(LocalDateTime.now().minusMinutes(10));
        parkEntity.setPricePerMinute(BigDecimal.valueOf(2));
        BigDecimal expectedTotalPrice = BigDecimal.valueOf(20);

        //When
        BigDecimal actual = ParkCalculator.calculateParkingPrice(parkEntity);

        //Then
        assertAll("Total Price Result Control",
                () -> assertNotNull(actual),
                () -> assertEquals(expectedTotalPrice, actual));
    }

    @Test
    void should_calculate_parking_price_with_different_values(){
        //Given
        ParkEntity parkEntity = new ParkEntity();
        parkEntity.setLastModifiedDate(LocalDateTime.now().minusMinutes(45));
        parkEntity.setPricePerMinute(BigDecimal.valueOf(2));
        BigDecimal expectedTotalPrice = BigDecimal.valueOf(90);

        //When
        BigDecimal actual = ParkCalculator.calculateParkingPrice(parkEntity);

        //Then
        assertAll("Total Price Result Control",
                () -> assertNotNull(actual),
                () -> assertEquals(expectedTotalPrice, actual));
    }

}
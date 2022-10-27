package com.aa.fleet.model.converter;

import com.aa.fleet.model.dto.ParkRequestDto;
import com.aa.fleet.model.dto.ParkResponseDto;
import com.aa.fleet.persistence.entity.ParkEntity;
import com.aa.fleet.persistence.entity.VehicleInfoEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VehicleConverter {
    public static VehicleInfoEntity convertToVehicleInfoEntity(ParkEntity parkEntity, ParkRequestDto parkRequestDto){
        return VehicleInfoEntity.builder()
                .floor(parkEntity.getFloor())
                .height(parkRequestDto.getHeight())
                .weight(parkRequestDto.getWeight())
                .plate(parkRequestDto.getPlate())
                .parkEntity(parkEntity)
                .type(parkRequestDto.getVehicleType().getVehicleId())
                .build();
    }

    public static ParkResponseDto entityToParkResponseDto(VehicleInfoEntity entity, BigDecimal totalPrice) {
        return ParkResponseDto.builder()
                .plate(entity.getPlate())
                .floor(entity.getFloor())
                .pricePerMinute(entity.getParkEntity().getPricePerMinute())
                .totalPrice(totalPrice)
                .build();
    }
}

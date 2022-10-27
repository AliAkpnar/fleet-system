package com.aa.fleet.util;

import com.aa.fleet.exception.PriceCanNotCalculateException;
import com.aa.fleet.persistence.entity.ParkEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ParkCalculator {
    public static BigDecimal calculateParkingPrice(ParkEntity entity){
        LocalDateTime now = LocalDateTime.now();
        if(Optional.ofNullable(entity).isPresent() && entity.getLastModifiedDate().isAfter(now))
            throw new PriceCanNotCalculateException();

        long minutes = ChronoUnit.MINUTES.between(entity.getLastModifiedDate(), now);
        return entity.getPricePerMinute().multiply(BigDecimal.valueOf(minutes));
    }
}

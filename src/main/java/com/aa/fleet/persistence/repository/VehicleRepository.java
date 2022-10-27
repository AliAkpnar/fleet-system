package com.aa.fleet.persistence.repository;


import com.aa.fleet.persistence.entity.VehicleInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VehicleRepository extends JpaRepository<VehicleInfoEntity, String> {
    Optional<VehicleInfoEntity> findByPlate(String plate);
}

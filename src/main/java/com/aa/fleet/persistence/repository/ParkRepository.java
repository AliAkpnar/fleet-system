package com.aa.fleet.persistence.repository;

import com.aa.fleet.persistence.entity.ParkEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParkRepository extends JpaRepository<ParkEntity, Long> {
    List<ParkEntity> findAll();
}

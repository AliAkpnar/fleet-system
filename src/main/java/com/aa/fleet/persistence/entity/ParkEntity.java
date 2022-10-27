package com.aa.fleet.persistence.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "park")
@EntityListeners(AuditingEntityListener.class)
public class ParkEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int floor;

    private Double totalWeight;

    private Double remainingWeight;

    @Column
    private BigDecimal pricePerMinute;

    @OneToMany(mappedBy = "floor", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<VehicleInfoEntity> parkList = new ArrayList<>();

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;
}

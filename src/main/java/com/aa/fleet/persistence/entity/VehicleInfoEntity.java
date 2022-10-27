package com.aa.fleet.persistence.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "vehicle_info")
@EntityListeners(AuditingEntityListener.class)
public class VehicleInfoEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double weight;

    @Column(nullable = false)
    private Double height;

    @Column(nullable = false)
    private int type;

    @Column
    private String plate;

    @Column(insertable = false, updatable = false)
    private int floor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "floor")
    private ParkEntity parkEntity;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

}

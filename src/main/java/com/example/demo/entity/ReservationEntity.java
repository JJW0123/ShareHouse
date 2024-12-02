package com.example.demo.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "reservation")
public class ReservationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long houseId;
    private String userId;
    private LocalDateTime reservationDate;

    @Builder
    public static ReservationEntity toReservationEntity(Long houseId, String userId, LocalDateTime reservationDate) {
        ReservationEntity reservationEntity = new ReservationEntity();

        reservationEntity.houseId = houseId;
        reservationEntity.userId = userId;
        reservationEntity.reservationDate = reservationDate;

        return reservationEntity;
    }

}

package com.example.demo.DTO;

import java.time.LocalDateTime;

import com.example.demo.entity.ReservationEntity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReservationDTO {
    private Long id;
    private Long houseId;
    private String userId;
    private LocalDateTime reservationDate;

    private String userPhone;
    private String userName;

    public static ReservationDTO toReservationDTO(ReservationEntity reservationEntity) {
        ReservationDTO reservationDTO = new ReservationDTO();

        reservationDTO.setId(reservationEntity.getId());
        reservationDTO.setHouseId(reservationEntity.getHouseId());
        reservationDTO.setUserId(reservationEntity.getUserId());
        reservationDTO.setReservationDate(reservationEntity.getReservationDate());

        return reservationDTO;
    }
}

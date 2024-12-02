package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.ReservationEntity;
import com.example.demo.repository.ReservationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRep;

    // 하우스 예약하기
    public void save(Long houseId, String userId, LocalDateTime reservationDate) {
        ReservationEntity reservationEntity = ReservationEntity.toReservationEntity(houseId, userId, reservationDate);
        reservationRep.save(reservationEntity);
    }

    // 예약한 하우스 조회(예약을 하지 않은 경우에는 null 반환)
    public Optional<ReservationEntity> getReservation(String userId) {
        // 유저id로 예약목록 찾기
        ReservationEntity reservationEntity = reservationRep.findByUserId(userId);
        return Optional.ofNullable(reservationEntity);
    }

    // 예약 취소하기
    @Transactional // jakarta.persistence.TransactionRequiredException 에러 발생해서 해당 어노테이션 추가
    public void delete(String userId) {
        reservationRep.deleteByUserId(userId);
    }
}

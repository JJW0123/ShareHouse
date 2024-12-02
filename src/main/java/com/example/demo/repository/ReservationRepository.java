package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.ReservationEntity;

public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {
    ReservationEntity findByUserId(String userId);

    void deleteByUserId(String userId);

    List<ReservationEntity> findAllByHouseId(Long houseId);
}

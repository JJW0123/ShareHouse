package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.HouseEntity;

public interface HouseRepository extends JpaRepository<HouseEntity, Integer> {
    // 하우스 아이디에 해당하는 하우스의 정보 받아오기
    HouseEntity findById(Long houseId);
}

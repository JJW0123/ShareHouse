package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.PhotoEntity;

public interface PhotoRepository extends JpaRepository<PhotoEntity, Long> {
    // 하우스의 첫 번째 사진만 가져오기
    PhotoEntity findFirstByHouseId(Long houseId);

    // 하우스의 모든 사진 가져오기
    List<PhotoEntity> findAllByHouseId(Long houseId);
}

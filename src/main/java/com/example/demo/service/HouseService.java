package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.DTO.HouseDTO;
import com.example.demo.entity.HouseEntity;
import com.example.demo.repository.HouseRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HouseService {
    private final HouseRepository houseRep;

    // 하우스 등록(하우스 아이디 반환)
    public Long save(HouseDTO houseDTO) {
        HouseEntity houseEntity = HouseEntity.toHouseEntity(houseDTO);
        houseRep.save(houseEntity);
        return houseEntity.getId();
    }
}

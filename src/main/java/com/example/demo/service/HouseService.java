package com.example.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    // 모든 하우스 DB 불러오기
    public List<HouseDTO> getAllHouses() {
        // findAll로 DB에서 모든 하우스 불러오고
        List<HouseEntity> houseEntityList = houseRep.findAll();

        // DTO로 형변환
        List<HouseDTO> houseDTOs = houseEntityList.stream().map(HouseDTO::toHouseDTO).collect(Collectors.toList());

        return houseDTOs;
    }

    // 특정한 키워드로 하우스 DB 불러오기
    public Optional<List<HouseDTO>> getAllHouseByKeyword(String keyword) {

        // 시/도 검색
        List<HouseEntity> houseEntityList = houseRep.findAllByFirstAddr(keyword);
        if (houseEntityList.isEmpty()) { // 검색결과 없다면
            // 시/군/구 검색
            houseEntityList = houseRep.findAllBySecondAddr(keyword);
        }

        // DTO로 형변환
        List<HouseDTO> houseDTOs = houseEntityList.stream().map(HouseDTO::toHouseDTO)
                .collect(Collectors.toList());

        return Optional.ofNullable(houseDTOs);
    }

    // 하나의 하우스 DB 불러오기
    public HouseDTO getOneHouse(Long houseId) {
        HouseEntity houseEntity = houseRep.findById(houseId);
        HouseDTO houseDTO = HouseDTO.toHouseDTO(houseEntity);

        return houseDTO;
    }

    // 하우스 주인 id에 해당하는 모든 하우스 불러오기
    public Optional<List<HouseDTO>> getAllHouseByOwnerId(String ownerId) {
        List<HouseEntity> houseEntityList = houseRep.findAllByOwnerId(ownerId);
        List<HouseDTO> houseDTOs = houseEntityList.stream().map(HouseDTO::toHouseDTO).collect(Collectors.toList());

        return Optional.ofNullable(houseDTOs);
    }

    @Transactional // jakarta.persistence.TransactionRequiredException 에러 발생해서 해당 어노테이션 추가
    public void delete(Long houseId) {
        houseRep.deleteById(houseId);
    }
}

package com.example.demo.entity;

import com.example.demo.DTO.HouseDTO;

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
@Table(name = "house") // 엔티티명과 테이블 이름이 다르므로 어노테이션 추가
public class HouseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int deposit;
    private int monthly_rent;
    private int area;
    private int person_count;
    private String description;

    private String firstAddr;
    private String secondAddr;
    private String thirdAddr;
    private String fourthAddr;

    private String ownerId;

    @Builder
    public static HouseEntity toHouseEntity(HouseDTO houseDTO) {
        HouseEntity houseEntity = new HouseEntity();

        houseEntity.id = houseDTO.getHouseId();
        houseEntity.name = houseDTO.getHouseName();
        houseEntity.deposit = houseDTO.getHouseDeposit();
        houseEntity.monthly_rent = houseDTO.getHouseMonthly_rent();
        houseEntity.area = houseDTO.getHouseArea();
        houseEntity.person_count = houseDTO.getHousePerson_count();
        houseEntity.description = houseDTO.getHouseDescription();

        houseEntity.firstAddr = houseDTO.getFirstAddr();
        houseEntity.secondAddr = houseDTO.getSecondAddr();
        houseEntity.thirdAddr = houseDTO.getThirdAddr();
        houseEntity.fourthAddr = houseDTO.getFourthAddr();

        houseEntity.ownerId = houseDTO.getOwnerId();

        return houseEntity;
    }
}

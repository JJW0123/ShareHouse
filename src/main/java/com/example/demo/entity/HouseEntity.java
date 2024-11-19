package com.example.demo.entity;

import com.example.demo.DTO.HouseDTO;

import jakarta.persistence.Entity;
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
    private Long id;

    private String name;

    private Long addr;

    private int deposit;

    private int monthly_rent;

    private int area;

    private int person_count;

    @Builder
    public static HouseEntity toHouseEntity(HouseDTO houseDTO) {
        HouseEntity houseEntity = new HouseEntity();

        houseEntity.id = houseDTO.getHouseId();
        houseEntity.name = houseDTO.getHouseName();
        houseEntity.addr = houseDTO.getHouseAddr();
        houseEntity.deposit = houseDTO.getHouseDeposit();
        houseEntity.monthly_rent = houseDTO.getHouseMonthly_rent();
        houseEntity.area = houseDTO.getHouseArea();
        houseEntity.person_count = houseDTO.getHousePerson_count();

        return houseEntity;
    }
}

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

    private String first_addr;
    private String second_addr;
    private String third_addr;
    private String fourth_addr;

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

        houseEntity.first_addr = houseDTO.getFirst_addr();
        houseEntity.second_addr = houseDTO.getSecond_addr();
        houseEntity.third_addr = houseDTO.getThird_addr();
        houseEntity.fourth_addr = houseDTO.getFourth_addr();

        return houseEntity;
    }
}

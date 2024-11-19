package com.example.demo.DTO;

import com.example.demo.entity.HouseEntity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class HouseDTO {
    private Long houseId;
    private String houseName;
    private Long houseAddr;
    private int houseDeposit;
    private int houseMonthly_rent;
    private int houseArea;
    private int housePerson_count;

    public static HouseDTO toHouseDTO(HouseEntity houseEntity) {
        HouseDTO houseDTO = new HouseDTO();

        houseDTO.setHouseId(houseEntity.getId());
        houseDTO.setHouseName(houseEntity.getName());
        houseDTO.setHouseAddr(houseEntity.getAddr());
        houseDTO.setHouseDeposit(houseEntity.getDeposit());
        houseDTO.setHouseMonthly_rent(houseEntity.getMonthly_rent());
        houseDTO.setHouseArea(houseEntity.getArea());
        houseDTO.setHousePerson_count(houseEntity.getPerson_count());

        return houseDTO;
    }
}

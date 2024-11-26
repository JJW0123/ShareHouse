package com.example.demo.DTO;

import com.example.demo.entity.HouseEntity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class HouseDTO {
    private Long houseId;
    private String houseName;
    private int houseDeposit;
    private int houseMonthly_rent;
    private int houseArea;
    private int housePerson_count;
    private String houseDescription;

    private String first_addr;
    private String second_addr;
    private String third_addr;
    private String fourth_addr;

    public static HouseDTO toHouseDTO(HouseEntity houseEntity) {
        HouseDTO houseDTO = new HouseDTO();

        houseDTO.setHouseId(houseEntity.getId());
        houseDTO.setHouseName(houseEntity.getName());
        houseDTO.setHouseDeposit(houseEntity.getDeposit());
        houseDTO.setHouseMonthly_rent(houseEntity.getMonthly_rent());
        houseDTO.setHouseArea(houseEntity.getArea());
        houseDTO.setHousePerson_count(houseEntity.getPerson_count());
        houseDTO.setHouseDescription(houseEntity.getDescription());

        houseDTO.setFirst_addr(houseEntity.getFirst_addr());
        houseDTO.setSecond_addr(houseEntity.getSecond_addr());
        houseDTO.setThird_addr(houseEntity.getThird_addr());
        houseDTO.setFourth_addr(houseEntity.getFourth_addr());

        return houseDTO;
    }
}

package com.example.demo.DTO;

import java.util.List;

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

    // 주소(1.시/도, 2.시/군/구, 3.도로명+건물번호, 4.상세주소)
    private String firstAddr;
    private String secondAddr;
    private String thirdAddr;
    private String fourthAddr;

    // 하우스 이미지 하나(AWS S3 url)
    private String img_url;

    // 하우스 이미지 전부(AWS S3 url)
    private List<String> img_url_list;

    private String ownerId;

    public static HouseDTO toHouseDTO(HouseEntity houseEntity) {
        HouseDTO houseDTO = new HouseDTO();

        houseDTO.setHouseId(houseEntity.getId());
        houseDTO.setHouseName(houseEntity.getName());
        houseDTO.setHouseDeposit(houseEntity.getDeposit());
        houseDTO.setHouseMonthly_rent(houseEntity.getMonthly_rent());
        houseDTO.setHouseArea(houseEntity.getArea());
        houseDTO.setHousePerson_count(houseEntity.getPerson_count());
        houseDTO.setHouseDescription(houseEntity.getDescription());

        houseDTO.setFirstAddr(houseEntity.getFirstAddr());
        houseDTO.setSecondAddr(houseEntity.getSecondAddr());
        houseDTO.setThirdAddr(houseEntity.getThirdAddr());
        houseDTO.setFourthAddr(houseEntity.getFourthAddr());

        houseDTO.setOwnerId(houseEntity.getOwnerId());

        return houseDTO;
    }
}

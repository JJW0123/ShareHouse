package com.example.demo.DTO;

import com.example.demo.entity.AddressEntity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AddressDTO {
    private Long addressId;
    private String first_addr;
    private String second_addr;
    private String third_addr;
    private String fourth_addr;

    public static AddressDTO toUserDTO(AddressEntity addressEntity) {
        AddressDTO addressDTO = new AddressDTO();

        addressDTO.setAddressId(addressEntity.getId());
        addressDTO.setFirst_addr(addressEntity.getFirst_addr());
        addressDTO.setSecond_addr(addressEntity.getSecond_addr());
        addressDTO.setThird_addr(addressEntity.getThird_addr());
        addressDTO.setFourth_addr(addressEntity.getFourth_addr());

        return addressDTO;
    }
}

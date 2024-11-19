package com.example.demo.entity;

import com.example.demo.DTO.AddressDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "address") // 엔티티명과 테이블 이름이 다르므로 어노테이션 추가
public class AddressEntity {
    @Id
    private Long id;

    private String first_addr;

    private String second_addr;

    private String third_addr;

    private String fourth_addr;

    @Builder
    public static AddressEntity toAddressEntity(AddressDTO addressDTO) {
        AddressEntity addressEntity = new AddressEntity();

        addressEntity.id = addressDTO.getAddressId();
        addressEntity.first_addr = addressDTO.getFirst_addr();
        addressEntity.second_addr = addressDTO.getSecond_addr();
        addressEntity.third_addr = addressDTO.getThird_addr();
        addressEntity.fourth_addr = addressDTO.getFourth_addr();

        return addressEntity;
    }
}

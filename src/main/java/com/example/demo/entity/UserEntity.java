package com.example.demo.entity;

import com.example.demo.DTO.UserDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "user") // 엔티티명과 테이블 이름이 다르므로 어노테이션 추가
public class UserEntity {
    @Id
    private String id;

    private String password;

    private String name;

    private String phone;

    private Long reserved_house;

    private Long registered_house;

    @Builder
    public static UserEntity toUserEntity(UserDTO userDTO) {
        UserEntity userEntity = new UserEntity();

        userEntity.id = userDTO.getUserId();
        userEntity.name = userDTO.getUserName();
        userEntity.password = userDTO.getUserPassword();
        userEntity.phone = userDTO.getUserPhone();

        return userEntity;
    }
}

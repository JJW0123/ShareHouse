package com.example.demo.DTO;

import com.example.demo.entity.UserEntity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserDTO {
    private String userId;
    private String userName;
    private String userPassword;
    private String userPhone;

    public static UserDTO toUserDTO(UserEntity userEntity) {
        UserDTO userDTO = new UserDTO();

        userDTO.setUserId(userEntity.getId());
        userDTO.setUserName(userEntity.getName());
        userDTO.setUserPassword(userEntity.getPassword());
        userDTO.setUserPhone(userEntity.getPhone());

        return userDTO;
    }
}

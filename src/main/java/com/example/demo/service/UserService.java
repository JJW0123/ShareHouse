package com.example.demo.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.DTO.UserDTO;
import com.example.demo.entity.UserEntity;
import com.example.demo.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor // final로 선언된 생성자 생성해주는 어노테이션
public class UserService {
    private final UserRepository userRep;

    // 회원가입시 아이디 중복 체크
    public boolean checkIdDuplicate(String UserId) {
        return userRep.existsById(UserId);
    }

    // 회원가입
    public void save(UserDTO userDTO) {
        UserEntity userEntity = UserEntity.toUserEntity(userDTO);
        userRep.save(userEntity);
    }

    // 로그인
    public UserEntity login(String id, String password) {
        Optional<UserEntity> optionalUserEntity = userRep.findById(id);

        // DB에 id가 존재하지 않는다면 null 리턴
        if (optionalUserEntity.isEmpty()) {
            return null;
        }

        UserEntity user = optionalUserEntity.get();

        // DB에 저장된 패스워드와 다르면 null 리턴
        if (!user.getPassword().equals(password)) {
            return null;
        }

        return user;
    }

    public UserEntity getUser(String id) {
        return userRep.findById(id).get();
    }
}

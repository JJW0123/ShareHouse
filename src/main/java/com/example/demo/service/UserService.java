package com.example.demo.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.DTO.UserDTO;
import com.example.demo.entity.UserEntity;
import com.example.demo.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor // final로 선언된 생성자 생성해주는 어노테이션
public class UserService {
    private final UserRepository userRep;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    // 회원가입
    public void joinProcess(UserDTO userDTO) {

        // name 중복이라면 프로세스 중단
        Boolean isExist = userRepository.existsById(userDTO.getUserId());
        if (isExist) {
            return;
        }

        // 패스워드는 암호화하고 role 추가해서 entity 형태로 repository.save()
        UserEntity data = new UserEntity();

        data.setId(userDTO.getUserId());
        data.setPassword(bCryptPasswordEncoder.encode(userDTO.getUserPassword()));
        data.setName(userDTO.getUserName());
        data.setPhone(userDTO.getUserPhone());
        data.setRole("ROLE_USER");

        userRepository.save(data);
    }

    // 회원가입시 아이디 중복 체크
    public boolean checkIdDuplicate(String UserId) {
        return userRep.existsById(UserId);
    }

    public UserEntity getUser(String id) {
        return userRep.findById(id).get();
    }
}

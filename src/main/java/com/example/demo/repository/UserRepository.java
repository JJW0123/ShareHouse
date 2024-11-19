package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.entity.UserEntity;

public interface UserRepository extends CrudRepository<UserEntity, String> {

}

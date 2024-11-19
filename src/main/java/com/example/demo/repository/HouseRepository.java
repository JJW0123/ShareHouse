package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.entity.HouseEntity;

public interface HouseRepository extends CrudRepository<HouseEntity, Integer> {

}

package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.entity.AddressEntity;

public interface AddressRepository extends CrudRepository<AddressEntity, Integer> {

}

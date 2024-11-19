package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.entity.PhotoEntity;

public interface PhotoRepository extends CrudRepository<PhotoEntity, Long> {

}

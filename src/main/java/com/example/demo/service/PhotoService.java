package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.entity.PhotoEntity;
import com.example.demo.repository.PhotoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PhotoService {
    private final PhotoRepository photoRep;

    public void save(String path, Long houseId) {
        PhotoEntity photoEntity = PhotoEntity.toPhotoEntity(path, houseId);
        photoRep.save(photoEntity);
    }
}

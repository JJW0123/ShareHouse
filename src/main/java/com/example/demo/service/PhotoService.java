package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

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

    // 하우스 id 받아서 사진 URL 반환(사진 한 장만)
    public String getPhotoUrl(Long houseId) {
        PhotoEntity photoEntity = photoRep.findFirstByHouseId(houseId);
        return photoEntity.getPath();
    }

    // 하우스 id 받아서 사진 URL 반환(사진 여러장)
    public List<String> getAllPhotoUrl(Long houseId) {
        List<PhotoEntity> photoEntityList = photoRep.findAllByHouseId(houseId);

        List<String> photoUrls = new ArrayList<>();

        // 각 사진 엔티티에서 URL 추출
        for (PhotoEntity photoEntity : photoEntityList) {
            photoUrls.add(photoEntity.getPath());
        }

        return photoUrls;
    }
}

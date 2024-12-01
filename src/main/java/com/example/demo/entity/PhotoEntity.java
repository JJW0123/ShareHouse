package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "photo") // 엔티티명과 테이블 이름이 다르므로 어노테이션 추가
public class PhotoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String path;
    private Long houseId;

    @Builder
    public static PhotoEntity toPhotoEntity(String path, Long houseId) {
        PhotoEntity photoEntity = new PhotoEntity();

        photoEntity.path = path;
        photoEntity.houseId = houseId;

        return photoEntity;
    }
}

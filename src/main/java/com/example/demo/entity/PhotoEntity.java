package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
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
    @GeneratedValue
    private Long id;

    private String path;

    private String name;

    private Long house_id;

    @Builder
    public PhotoEntity(String path, String name, Long house_id) {
        this.path = path;
        this.name = name;
        this.house_id = house_id;
    }
}

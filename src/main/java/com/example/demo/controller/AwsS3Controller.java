package com.example.demo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.service.AwsS3Service;

import lombok.RequiredArgsConstructor;

// TODO:추후 삭제하기
@RestController
@RequiredArgsConstructor
@RequestMapping("/test/file") // 파일 업로드, 삭제 테스트용 Url
public class AwsS3Controller {

    private final AwsS3Service awsS3Service;

    @PostMapping
    public ResponseEntity<List<String>> uploadFiles(List<MultipartFile> multipartFile) {
        return ResponseEntity.ok((awsS3Service.uploadFiles(multipartFile)));
    }

    @DeleteMapping
    public ResponseEntity<String> deleteFile(@RequestParam String fileName) {
        awsS3Service.deleteFile(fileName);
        return ResponseEntity.ok(fileName);
    }
}
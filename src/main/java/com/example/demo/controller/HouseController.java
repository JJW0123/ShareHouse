package com.example.demo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.DTO.HouseDTO;
import com.example.demo.service.AwsS3Service;
import com.example.demo.service.HouseService;
import com.example.demo.service.PhotoService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class HouseController {

    private final HouseService houseService;
    private final PhotoService photoService;
    private final AwsS3Service awsS3Service;

    @PostMapping("/reserve")
    public String reserveHouse(
            @ModelAttribute HouseDTO houseDTO,
            @RequestParam("housePhoto") List<MultipartFile> housePhoto) {

        // 하우스 정보 저장하고 id 반환
        Long houseId = houseService.save(houseDTO);

        // 이미지 업로드하고 각 파일 Url을 리스트로 반환
        List<String> fileUrlList = awsS3Service.uploadFiles(housePhoto);

        // 각 파일 Url과 하우스 id를 포토 DB에 저장
        for (String fileUrl : fileUrlList) {
            photoService.save(fileUrl, houseId);
        }

        return "redirect:/";
    }
}

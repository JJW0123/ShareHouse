package com.example.demo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.DTO.HouseDTO;
import com.example.demo.service.AwsS3Service;
import com.example.demo.service.HouseService;
import com.example.demo.service.PhotoService;

import lombok.RequiredArgsConstructor;

// TODO: Controller 별 url 경로 다듬기
@Controller
@RequiredArgsConstructor
public class HouseController {

    private final HouseService houseService;
    private final PhotoService photoService;
    private final AwsS3Service awsS3Service;

    // 하우스 등록 페이지
    @PostMapping("/register")
    public String registerHouse(
            @ModelAttribute HouseDTO houseDTO,
            @RequestParam("housePhoto") List<MultipartFile> housePhoto) {

        // 하우스 정보 저장하고 id 반환
        Long houseId = houseService.save(houseDTO);

        // 이미지 업로드하고 각 파일 Url을 리스트로 반환
        List<String> fileUrlList = awsS3Service.uploadFiles(housePhoto);

        // 포토 DB에 각 파일 Url과 하우스 id 저장
        for (String fileUrl : fileUrlList) {
            photoService.save(fileUrl, houseId);
        }

        // TODO:하우스 등록 시 세션에서 유저 정보 받아와 User DB 업데이트 하기
        return "redirect:/";
    }

    // 하우스 조회 페이지
    @GetMapping("/search")
    public String getHouseInfo(Model model) {
        // 데이터베이스에서 모든 하우스 데이터를 가져옴
        List<HouseDTO> houseDTOList = houseService.getAllHouses();

        // TODO: 이미지 크기 조정
        // S3에서 이미지 URL 받아와서 DTO에 넣기
        for (HouseDTO houseDTO : houseDTOList) {
            houseDTO.setImg_url(photoService.getPhotoUrl(houseDTO.getHouseId()));
        }

        // 모델에 DTO 추가
        model.addAttribute("houseList", houseDTOList);

        return "search";
    }

    // TODO: 텍스트 디자인 수정하기
    // 하우스 상세 조회 페이지
    @GetMapping("/detail/{house_id}")
    public String getHouseDetail(Model model, @PathVariable("house_id") Long house_id) {
        HouseDTO houseDTO = houseService.getOneHouse(house_id);

        houseDTO.setImg_url_list(photoService.getAllPhotoUrl(houseDTO.getHouseId()));

        model.addAttribute("houseInfo", houseDTO);

        return "house_detail";
    }
    // TODO:예약하기 버튼 추가

}

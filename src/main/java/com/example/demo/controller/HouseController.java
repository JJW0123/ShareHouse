package com.example.demo.controller;

import java.time.LocalDateTime;
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
import com.example.demo.DTO.ReservationDTO;
import com.example.demo.entity.UserEntity;
import com.example.demo.service.AwsS3Service;
import com.example.demo.service.HouseService;
import com.example.demo.service.PhotoService;
import com.example.demo.service.ReservationService;
import com.example.demo.service.UserService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

// url requestMapping 추가 시 css 파일 경로를 thymeleaf 상대경로로 바꿔주기
@Controller
@RequiredArgsConstructor
public class HouseController {

    private final HouseService houseService;
    private final PhotoService photoService;
    private final AwsS3Service awsS3Service;
    private final UserService userService;
    private final ReservationService reservationService;

    // 하우스 등록 페이지(GET)
    @GetMapping("/register")
    public String register(HttpSession session, Model model) {

        UserEntity userEntity = (UserEntity) session.getAttribute("loginUser");
        model.addAttribute("isLoggedIn", userEntity != null);

        // 로그인하지 않은 상태라면 로그인 페이지로 이동
        if (userEntity == null) {
            return "redirect:/login";
        }

        return "register";
    }

    // 하우스 등록(POST)
    @PostMapping("/register")
    public String registerHouse(
            @ModelAttribute HouseDTO houseDTO,
            @RequestParam("housePhoto") List<MultipartFile> housePhoto,
            HttpSession session,
            Model model) {

        // 세션에서 id 받아와 하우스 주인으로 저장
        UserEntity userEntity = (UserEntity) session.getAttribute("loginUser");
        houseDTO.setOwnerId(userEntity.getId());

        // 하우스DTO 저장하고 id 반환
        Long houseId = houseService.save(houseDTO);

        // 이미지를 AWS S3에 업로드하고 파일 Url을 리스트로 반환
        List<String> fileUrlList = awsS3Service.uploadFiles(housePhoto);

        // 파일 Url과 하우스 id 저장
        for (String fileUrl : fileUrlList) {
            photoService.save(fileUrl, houseId);
        }

        // 등록 성공 메세지 띄우고 마이페이지 - 등록한 하우스로 리다이렉트
        model.addAttribute("message", "성공적으로 등록 되었습니다.");
        model.addAttribute("redirectUrl", "/mypage_registration");
        return "alert";
    }

    // 하우스 조회 페이지(GET)
    @GetMapping("/search")
    public String getHouseInfo(HttpSession session, Model model) {
        UserEntity userEntity = (UserEntity) session.getAttribute("loginUser");
        model.addAttribute("isLoggedIn", userEntity != null);

        // 데이터베이스에서 모든 하우스 데이터를 가져옴
        List<HouseDTO> houseDTOList = houseService.getAllHouses();

        // S3에서 이미지 URL 받아와서 DTO에 넣기
        for (HouseDTO houseDTO : houseDTOList) {
            houseDTO.setImg_url(photoService.getPhotoUrl(houseDTO.getHouseId()));
        }

        // 모델에 DTO 추가
        model.addAttribute("houseList", houseDTOList);

        return "search";
    }

    // 하우스 상세 조회 페이지(GET)
    @GetMapping("/detail/{house_id}")
    public String getHouseDetail(Model model, @PathVariable("house_id") Long house_id, HttpSession session) {

        // 하우스 정보 받아오기
        HouseDTO houseDTO = houseService.getOneHouse(house_id);
        houseDTO.setImg_url_list(photoService.getAllPhotoUrl(houseDTO.getHouseId()));
        model.addAttribute("houseInfo", houseDTO);

        // 로그인 여부 확인
        UserEntity userEntity = (UserEntity) session.getAttribute("loginUser");
        model.addAttribute("isLoggedIn", userEntity != null);

        // 조회하는 유저가 하우스 주인이라면 예약 현황 표시
        if (userEntity != null && userEntity.getId().equals(houseDTO.getOwnerId())) {
            List<ReservationDTO> reservations = reservationService.getAllByHouseId(house_id);
            for (ReservationDTO reservationEntity : reservations) {
                UserEntity user = userService.getUser(reservationEntity.getUserId());
                reservationEntity.setUserPhone(user.getPhone());
                reservationEntity.setUserName(user.getName());
            }
            model.addAttribute("reservations", reservations);
        }
        return "house_detail";
    }

    // 하우스 예약(POST)
    @PostMapping("/reserve")
    public String reserveHouse(
            @RequestParam("houseId") Long houseId,
            HttpSession session,
            Model model) {

        UserEntity userEntity = (UserEntity) session.getAttribute("loginUser");

        // 로그인하지 않은 상태라면 로그인 페이지로 이동
        if (userEntity == null) {
            return "redirect:/login";
        }

        // 이미 예약한 하우스가 있다면 예외처리
        if (!reservationService.getReservation(userEntity.getId()).isEmpty()) {
            model.addAttribute("message", "이미 예약중인 하우스가 있습니다");
            model.addAttribute("redirectUrl", "/detail/" + houseId);
            return "alert";
        }
        // DB에 예약 정보(하우스id, 예약한 유저id, 예약날짜) 저장
        reservationService.save(houseId, userEntity.getId(), LocalDateTime.now());

        // 예약 성공 메세지 띄우고 마이페이지 예약화면으로 리다이렉트
        model.addAttribute("message", "성공적으로 예약 되었습니다.");
        model.addAttribute("redirectUrl", "/mypage_reservation");
        return "alert";
    }

    // TODO: 도로명, 시/도 검색 추가하기
}

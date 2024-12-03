package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.DTO.HouseDTO;
import com.example.demo.DTO.UserDTO;
import com.example.demo.entity.ReservationEntity;
import com.example.demo.entity.UserEntity;
import com.example.demo.service.HouseService;
import com.example.demo.service.PhotoService;
import com.example.demo.service.ReservationService;
import com.example.demo.service.UserService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

// 로그인과 마이페이지의 기능을 담당하는 컨트롤러
@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final HouseService houseService;
    private final PhotoService photoService;
    private final ReservationService reservationService;

    // 로그인 페이지(GET)
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // 로그인(POST)
    @PostMapping("/login")
    public String login(String userId, String userPassword, HttpSession session, Model model) {
        UserEntity user = userService.login(userId, userPassword);

        // 로그인 실패시 알림 띄우기
        if (user == null) {
            model.addAttribute("message", "아이디나 비밀번호가 틀렸습니다.");
            model.addAttribute("redirectUrl", "/login");
            return "alert";
        }

        // 로그인 유저는 세션에 저장
        session.setAttribute("loginUser", user);
        return "redirect:/";
    }

    // 로그아웃(GET)
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    // 회원가입 페이지(GET)
    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    // 회원가입(POST)
    @PostMapping("/signup")
    public String signup(@ModelAttribute UserDTO userDTO, Model model) {

        // 아이디 중복 예외처리
        if (userService.checkIdDuplicate(userDTO.getUserId())) {
            model.addAttribute("message", "이미 존재하는 아이디입니다.");
            model.addAttribute("redirectUrl", "/signup");
            return "alert";
        }

        // 예외처리 통과하면 저장
        userService.save(userDTO);

        model.addAttribute("message", "회원가입에 성공했습니다.");
        model.addAttribute("redirectUrl", "/");
        return "alert";
    }

    // 마이페이지 - 예약한 하우스 조회 페이지(GET)
    @GetMapping("/mypage_reservation")
    public String reservation(HttpSession session, Model model) {
        UserEntity userEntity = (UserEntity) session.getAttribute("loginUser");
        if (userEntity != null) {
            model.addAttribute("isLoggedIn", true);
            model.addAttribute("userName", userEntity.getName()); // 로그인 사용자 이름
        }

        // 로그인하지 않은 상태라면 로그인 페이지로 이동
        if (userEntity == null) {
            return "redirect:/login";
        }
        // 예약한 하우스 불러오기
        Optional<ReservationEntity> reservationEntity = reservationService.getReservation(userEntity.getId());

        // 예약한 하우스 없을 경우 예외처리
        if (reservationEntity.isEmpty()) {
            model.addAttribute("message", "예약한 하우스가 없습니다.");
            model.addAttribute("redirectUrl", "/");
            return "alert";
        }

        HouseDTO houseDTO = houseService.getOneHouse(reservationEntity.get().getHouseId());

        // 하우스 사진 DTO에 추가하기
        houseDTO.setImg_url(photoService.getPhotoUrl(houseDTO.getHouseId()));

        // 모델에 예약한 하우스 정보 넘겨주기
        model.addAttribute("house", houseDTO);

        return "mypage_reservation";
    }

    // 마이페이지 - 예약 취소
    @GetMapping("/cancelReservation")
    public String cancelReservation(HttpSession session, Model model) {
        // 세션에서 유저 아이디 받아와 예약 취소하기
        UserEntity userEntity = (UserEntity) session.getAttribute("loginUser");

        reservationService.delete(userEntity.getId());

        // 예약 취소 성공 메세지 띄우고 메인화면으로 리다이렉트
        model.addAttribute("message", "성공적으로 예약 취소 되었습니다.");
        model.addAttribute("redirectUrl", "/");
        return "alert";
    }

    // 마이페이지 - 등록한 하우스 조회 페이지(GET)
    @GetMapping("/mypage_registration")
    public String registration(HttpSession session, Model model) {
        UserEntity userEntity = (UserEntity) session.getAttribute("loginUser");
        if (userEntity != null) {
            model.addAttribute("isLoggedIn", true);
            model.addAttribute("userName", userEntity.getName()); // 로그인 사용자 이름
        }

        // 로그인하지 않은 상태라면 로그인 페이지로 이동
        if (userEntity == null) {
            return "redirect:/login";
        }
        // 등록한 하우스 불러오기
        Optional<List<HouseDTO>> houseDTOList = houseService.getAllHouseByOwnerId(userEntity.getId());

        // 등록한 하우스 없을 경우 예외처리
        if (houseDTOList.isEmpty()) {
            model.addAttribute("message", "등록한 하우스가 없습니다.");
            model.addAttribute("redirectUrl", "/");
            return "alert";
        }

        // S3에서 이미지 URL 받아와서 DTO에 넣기
        for (HouseDTO houseDTO : houseDTOList.get()) {
            houseDTO.setImg_url(photoService.getPhotoUrl(houseDTO.getHouseId()));
        }

        // 모델에 DTO 추가
        model.addAttribute("houseList", houseDTOList.get());

        return "mypage_registration";
    }

    // 마이페이지 - 등록 취소
    @GetMapping("/cancelRegistration/{houseId}")
    public String cancelRegistration(@PathVariable("houseId") Long houseId, Model model) {
        houseService.delete(houseId);

        model.addAttribute("message", "하우스 등록을 취소했습니다.");
        model.addAttribute("redirectUrl", "/");
        return "alert";
    }
}

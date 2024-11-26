package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.DTO.UserDTO;
import com.example.demo.entity.UserEntity;
import com.example.demo.service.UserService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MainController {

    // 여기부터

    // 하우스 등록
    @GetMapping("/reserve")
    public String reserve() {
        return "reserve";
    }

    // 하우스 조회
    @GetMapping("/search")
    public String search() {
        return "search";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    @GetMapping("/house_detail")
    public String house_detail() {
        return "house_detail";
    }

    private final UserService userService;

    // 여기부터
    @GetMapping("/")
    public String home(HttpSession session, Model model) {
        // 세션에서 로그인 정보 가져오기
        UserEntity loginUser = (UserEntity) session.getAttribute("loginUser");
        if (loginUser != null) {
            model.addAttribute("isLoggedIn", true);
            model.addAttribute("userName", loginUser.getName()); // 로그인 사용자 이름
        } else {
            model.addAttribute("isLoggedIn", false);
        }
        return "index";
    }

    @PostMapping("/login")
    public String login(String userId, String userPassword, HttpSession session) {
        UserEntity user = userService.login(userId, userPassword);

        if (user == null) {
            return "login"; // 로그인 실패
        }

        session.setAttribute("loginUser", user); // 로그인 사용자 정보를 세션에 저장
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @PostMapping("/signup")
    public String signup(@ModelAttribute UserDTO userDTO) {
        // save 함수는 업데이트도 겸하고 있음
        userService.save(userDTO);
        // TODO: 아이디 중복일 경우 제약조건 추가하기
        return "redirect:/";
    }
}

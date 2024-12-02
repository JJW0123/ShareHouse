package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.entity.UserEntity;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

// TODO: favicon 수정하기

@Controller
@RequiredArgsConstructor
public class MainController {

    @GetMapping("/")
    public String home(HttpSession session, Model model) {
        // 세션에서 로그인 정보 가져오기
        UserEntity loginUser = (UserEntity) session.getAttribute("loginUser");
        if (loginUser != null) {
            model.addAttribute("isLoggedIn", true);
            // TODO: index.html 유저 이름 디자인 수정
            model.addAttribute("userName", loginUser.getName()); // 로그인 사용자 이름
        } else {
            model.addAttribute("isLoggedIn", false);
        }
        return "index";
    }
}

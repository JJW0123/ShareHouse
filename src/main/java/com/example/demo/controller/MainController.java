package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.DTO.UserDTO;
import com.example.demo.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor // final로 선언된 생성자 생성해주는 어노테이션
public class MainController {

    private final UserService userService;

    @GetMapping("/main")
    public String main() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/properties")
    public String properties() {
        return "properties";
    }

    @GetMapping("/property_single")
    public String property_single() {
        return "property-single";
    }

    @GetMapping("/contact")
    public String contact() {
        return "contact";
    }

    @GetMapping("/services")
    public String services() {
        return "services";
    }

    @PostMapping("/signup")
    public String signup(@ModelAttribute UserDTO userDTO) {
        // save 함수는 업데이트도 겸하고 있음
        userService.save(userDTO);
        // TODO: 아이디 중복일 경우 제약조건 추가하기
        return "redirect:/";
    }
}

package com.example.mvcpattern.controller;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller

public class FirstController {
    @GetMapping({"/bye"})
    public String seeYouNext(Model model) {
        model.addAttribute("nickname", "안녕하세요");
        return "goodbye";
    }
    @GetMapping({"/hi"})
    public String niceToMeetYou(Model model) {
    model.addAttribute("username", "오채빈");
        return "greetings";// greetings.mustache 파일 반환
    }
}

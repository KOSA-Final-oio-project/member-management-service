package com.oio.memberservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/member-service")
@RequiredArgsConstructor
public class LoginController {


    @GetMapping("/login")
    public String loginPage(){
        return "home";
    }


}

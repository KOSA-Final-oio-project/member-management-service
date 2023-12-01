package com.oio.memberservice.controller;

import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/member-service")
@RequiredArgsConstructor
public class LoginController {


    @GetMapping("/login")
    public String loginPage(){
        return "home";
    }



}

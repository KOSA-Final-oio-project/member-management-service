package com.oio.memberservice.controller;

import com.oio.memberservice.dto.LoginDto;
import com.oio.memberservice.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/member-service")
@RequiredArgsConstructor
public class LoginController {
    private final AuthenticationService authenticationService;

    @GetMapping("/login")
    @ResponseBody
    public String loginPage(){
        return "home";
    }


    @PostMapping("/login")
    public String login(@RequestBody LoginDto dto){
        String token = authenticationService.login(dto);
        return token;
    }


}

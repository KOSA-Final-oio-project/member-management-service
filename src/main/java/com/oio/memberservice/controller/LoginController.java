package com.oio.memberservice.controller;

import com.oio.memberservice.dto.LoginDto;
import com.oio.memberservice.dto.Token;
import com.oio.memberservice.entity.RefreshTokenEntity;
import com.oio.memberservice.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/member-service")
@RequiredArgsConstructor
public class LoginController {
    private final MemberService memberService;

    @PostMapping("/login")
    public Token login(@RequestBody LoginDto dto){
        Token result = memberService.login(dto);
        return result;
    }

    @PostMapping("/refresh")
    public Map<String,Object> validate(HttpServletRequest request,LoginDto dto){
        Map responseMap = new HashMap();
        String token = request.getHeader("refreshToken");
        Map<String, Object> result = memberService.validateRefreshToken(token, dto);
        if(result.get("result").equals("fail")){
            responseMap.put("result","다시로그인해주세요");
            return responseMap;
        }
        responseMap.put("result",result.get("result"));
        return responseMap;
    }


}

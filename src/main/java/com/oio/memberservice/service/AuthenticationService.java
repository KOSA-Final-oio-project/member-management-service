//package com.oio.memberservice.service;
//
//import com.oio.memberservice.dto.LoginDto;
//import com.oio.memberservice.dto.Token;
//import com.oio.memberservice.security.JwtTokenProvider;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class AuthenticationService {
//
//    private final AuthenticationManager authenticationManager;
//    private final JwtTokenProvider jwtTokenProvider;
//
//    public Token login(LoginDto loginDto) {
//        try {
//            // 사용자 인증을 위한 토큰 생성
//            Authentication authentication = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
//            );
//
//            // 인증이 성공하면 JWT 토큰 생성
//            Token token = jwtTokenProvider.generateToken(authentication);
//
//            // 생성된 토큰 반환
//            return token;
//        } catch (AuthenticationException e) {
//            // 인증 실패 처리
//            throw new BadCredentialsException("Invalid username or password");
//        }
//    }
//
//}

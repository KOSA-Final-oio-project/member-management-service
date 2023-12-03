package com.oio.memberservice.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;

    public CustomAuthenticationSuccessHandler(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        clearAuthenticationAttributes(request);

        String username = authentication.getName();

        // JWT 토큰 생성
        String token = jwtUtil.generateToken(username);

        // 헤더에 JWT 토큰 추가
        response.addHeader("token", "Bearer " + token);

        // 기타 응답 설정 (예: 상태 코드, 메시지 등)
        response.setStatus(HttpServletResponse.SC_OK);

        // super.onAuthenticationSuccess(request, response, authentication);
    }
}

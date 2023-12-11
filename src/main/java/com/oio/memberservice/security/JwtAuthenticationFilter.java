package com.oio.memberservice.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Value("${jwt.secret}")
    private String secretKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // 헤더에서 토큰 추출
        String header = request.getHeader("token");
        if (header != null && header.startsWith("Bearer ")) {
            try {
                System.out.println(header);
                String token = header.substring(7); // "Bearer " 이후의 토큰 부분 추출
                Jws<Claims> claimsJws = Jwts.parser()
                        .setSigningKey("user_token")
                        .parseClaimsJws(token);

                Claims claims = claimsJws.getBody();
                String username = claims.getSubject();

                if (username != null) {
                    Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, null);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (Exception e) {
                // 토큰이 유효하지 않은 경우 또는 예외 발생 시 처리
                e.printStackTrace();
                SecurityContextHolder.clearContext();
            }
        }

        filterChain.doFilter(request, response);
    }
}

package com.oio.memberservice.security;

import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtUtil {

    private final Environment env;

    public String generateToken(String username){
        System.out.println(env);
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis()+ Long.parseLong(env.getProperty("token.expiration_time"))))
                .signWith(SignatureAlgorithm.HS512,env.getProperty("token.secret"))
                .compact();
    }
}

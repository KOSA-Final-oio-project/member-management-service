package com.oio.memberservice.security;

import com.oio.memberservice.dto.LoginDto;
import com.oio.memberservice.dto.LoginResponseDto;
import com.oio.memberservice.dto.Token;
import com.oio.memberservice.entity.MemberEntity;
import com.oio.memberservice.entity.RefreshTokenEntity;
import com.oio.memberservice.repository.RefreshTokenRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final RefreshTokenRepository refreshTokenRepository;
    @Value("${security.jwt.token.access-key}")
    private String secretKey;

    @Value("${security.jwt.token.refresh-key}")
    private String refreshKey;

    @Value("800000000")
    private long validityInMilliseconds;

    @Value("800000000")
    private long refreshValidity;

    public Token generateToken(LoginDto dto) {

        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        // 이 부분에서 User의 nickname을 토큰에 포함시킬 수 있습니다.
        String accessToken =  Jwts.builder()
                .setSubject(dto.getEmail())
                .claim("nickname", dto.getNickname()) // nickname 추가
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        String refreshToken =  Jwts.builder()
                .setSubject(dto.getEmail())
                .claim("nickname", dto.getNickname()) // nickname 추가
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, refreshKey)
                .compact();

        RefreshTokenEntity entity = RefreshTokenEntity.builder()
                .username(dto.getEmail())
                .refreshToken(refreshToken).build();
        refreshTokenRepository.save(entity);

        return Token.builder().accessToken(accessToken).refreshToken(refreshToken)
                .key(dto.getEmail())
                .build();
    }

}

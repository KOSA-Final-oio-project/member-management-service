package com.oio.memberservice.repository;

import com.oio.memberservice.entity.RefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity,String> {

    Optional<RefreshTokenEntity> findByRefreshToken(String refreshToken);
    Optional<RefreshTokenEntity> findByUsername(String email);
}

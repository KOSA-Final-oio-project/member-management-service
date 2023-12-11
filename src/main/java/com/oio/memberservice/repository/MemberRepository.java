package com.oio.memberservice.repository;

import com.oio.memberservice.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity,Long> {

    Optional<MemberEntity> findByEmail(String email);

    Optional<MemberEntity> findByNickname(String nickname);

    Optional<MemberEntity> findByEmailAndPassword(String email,String password);
}

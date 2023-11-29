package com.oio.memberservice.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity,Long> {

    Optional<MemberEntity> findByEmail(String email);

    Optional<MemberEntity> findByNickname(String nickname);
}

package com.oio.memberservice.service;

import com.oio.memberservice.dto.*;
import com.oio.memberservice.entity.MemberEntity;
import com.oio.memberservice.entity.RefreshTokenEntity;
import com.oio.memberservice.repository.MemberRepository;
import com.oio.memberservice.repository.RefreshTokenRepository;
import com.oio.memberservice.s3.S3Service;
import com.oio.memberservice.security.JwtTokenProvider;
import com.oio.memberservice.status.MemberStatus;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final ModelMapper mapper;
    private final BCryptPasswordEncoder passwordEncoder;

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;
    private final S3Service s3Service;

    public void createMember(MemberRequestDto memberRequestDto, MultipartFile file) throws IOException {

        MemberEntity member = mapper.map(memberRequestDto, MemberEntity.class);
        member.changePassword(passwordEncoder.encode(memberRequestDto.getPassword()));
        String imgUrl = s3Service.upload(file);
        member.changeProfile(imgUrl);
        member.changeStatusToBasic();
        member.changeJoinDate(LocalDateTime.now());

        memberRepository.save(member);
    }

    public Token login(LoginDto loginDto){
        Optional<MemberEntity> findMember = memberRepository.findByEmail(loginDto.getEmail());

        if(!(findMember.isPresent()) ||
                !(passwordEncoder.matches(loginDto.getPassword(), findMember.get().getPassword()))){
            Token token = new Token();
            token.setAccessToken("fail");
            token.setRefreshToken("fail");
            return token;
        }

        MemberEntity member = findMember.get();
        LoginDto dto = mapper.map(member, LoginDto.class);
        Token token = jwtTokenProvider.generateToken(dto);
        return token;
    }

    public String idDupChk(String email) {
        Optional<MemberEntity> result = memberRepository.findByEmail(email);

        if (result.isPresent()) {
            return "이미 가입된 이메일입니다.";
        }
        return "사용가능한 이메일입니다.";
    }

    public String emailDupChk(String nickname) {
        Optional<MemberEntity> result = memberRepository.findByNickname(nickname);

        if (result.isPresent()) {
            return "이미 사용중인 닉네임입니다.";
        }
        return "사용가능한 닉네임입니다.";
    }


    public MemberResponseDto getMember(String memberNickname) {
        MemberEntity resultByNickname = memberRepository.findByNickname(memberNickname).orElseThrow(
                () -> new UsernameNotFoundException("요청하신 사용자를 찾을 수 없습니다.")
        );

        return mapper.map(resultByNickname, MemberResponseDto.class);


    }

    public void updateMember(String memberNickname, memberUpdateDto dto,MultipartFile file) throws IOException {
        MemberEntity memberEntity = memberRepository.findByNickname(memberNickname).orElseThrow(
                () -> new UsernameNotFoundException("수정할 회원이 없습니다.")
        );
        memberEntity.changePassword(passwordEncoder.encode(dto.getPassword()));
        String imgUrl = s3Service.upload(file);
        System.out.println(imgUrl);
        memberEntity.changeProfile(imgUrl);
        memberEntity.setName(dto.getName());
        memberEntity.setNickname(dto.getNickname());
        memberEntity.setEmail(dto.getEmail());
        memberEntity.setPhoneNumber(dto.getPhoneNumber());
        memberEntity.setProfile(dto.getProfile());
        memberRepository.save(memberEntity);
    }

    @Scheduled(fixedRate = 60000)
    public void deleteMemberByTime(){
        List<MemberEntity> members = memberRepository.findAll();

        for (MemberEntity member : members){
            if(member.getStatus().equals("탈퇴회원")){
                memberRepository.delete(member);
            }
        }
    }

    public void deleteMemberByNickname(String memberNickname) {
        MemberEntity member = memberRepository.findByNickname(memberNickname).orElseThrow(
                () ->  new UsernameNotFoundException("사용자를 찾을 수 없습니다.")
        );
        member.changeStatusToWithdrawal();
        memberRepository.save(member);
    }


    public void resetPassword(String memberEmail, String password) {
        MemberEntity member = memberRepository.findByEmail(memberEmail).orElseThrow(
                () -> new UsernameNotFoundException("사용자를 찾을 수 없습니다.")
        );

        member.changePassword(passwordEncoder.encode(password));
        memberRepository.save(member);
    }

    public Map<String,Object> validateRefreshToken(String token,LoginDto dto) {
        Map result = new HashMap();
        Optional<RefreshTokenEntity> refreshToken = refreshTokenRepository.findByUsername(dto.getEmail());
        if(!(refreshToken.isPresent())){
            result.put("result","fail");
            return result;
        }
        refreshTokenRepository.delete(refreshToken.get());

        Token newToken = jwtTokenProvider.generateToken(dto);
        result.put("result",newToken);
        return result;

    }
}

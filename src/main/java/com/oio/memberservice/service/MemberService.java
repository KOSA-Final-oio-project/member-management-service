package com.oio.memberservice.service;

import com.oio.memberservice.dto.MemberRequestDto;
import com.oio.memberservice.dto.MemberResponseDto;
import com.oio.memberservice.dto.memberUpdateDto;
import com.oio.memberservice.entity.MemberEntity;
import com.oio.memberservice.repository.MemberRepository;
import com.oio.memberservice.status.MemberStatus;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final ModelMapper mapper;
    private final BCryptPasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    public void createMember(MemberRequestDto memberRequestDto) throws UnsupportedEncodingException {

        MemberEntity member = mapper.map(memberRequestDto, MemberEntity.class);
        member.setPassword(passwordEncoder.encode(memberRequestDto.getPassword()));
        member.changeStatusToBasic();
        member.changeJoinDate(LocalDateTime.now());

        memberRepository.save(member);
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

    public void updateMember(String memberNickname, memberUpdateDto dto) {
        MemberEntity memberEntity = memberRepository.findByNickname(memberNickname).orElseThrow(
                () -> new RuntimeException("수정할 회원이 없습니다.")
        );

        memberEntity.setPassword(passwordEncoder.encode(dto.getPassword()));
        memberEntity.setName(dto.getName());
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
        member.setStatus(MemberStatus.탈퇴회원);
        memberRepository.save(member);
    }
}

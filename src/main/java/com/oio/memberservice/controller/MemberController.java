package com.oio.memberservice.controller;

import com.oio.memberservice.dto.*;
import com.oio.memberservice.service.MailService;
import com.oio.memberservice.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/member-service")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final MailService emailService;



    @PostMapping("/signup")
    public ResponseEntity<Map<String,String>> signUp(@RequestBody MemberRequestDto memberRequestDto){
        Map result = new HashMap();
        try {
            memberService.createMember(memberRequestDto);
            result.put("status","success");
        }catch (UnsupportedEncodingException e){
            result.put("status","error");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        }
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping("/member/{memberNickname}")
    public MemberResponseDto showMember(@PathVariable String memberNickname){
        MemberResponseDto member = memberService.getMember(memberNickname);
        return member;
    }

    @PutMapping("member/{memberNickname}")
    public ResponseEntity<String> updateMember(@PathVariable String memberNickname, @RequestBody memberUpdateDto dto){
        memberService.updateMember(memberNickname,dto);
        return ResponseEntity.status(HttpStatus.OK).body("수정완료");
    }

    @DeleteMapping("member/{memberNickname}")
    public ResponseEntity<String> deleteMember(@PathVariable String memberNickname){
        memberService.deleteMemberByNickname(memberNickname);
        return ResponseEntity.status(HttpStatus.OK).body("삭제완료");
    }


    @PostMapping("/email-chk")
    public String idDupChk(@RequestBody emailChkDto emailChkDto){
        String result = memberService.idDupChk(emailChkDto.getEmail());

        return result;
    }

    @PostMapping("/nickname-chk")
    public String nicknameDupChk(@RequestBody nicknameDto nicknameDto){
        String result = memberService.emailDupChk(nicknameDto.getNickname());

        return result;
    }

    @PostMapping("/member/{memberEmail}")
    public Map<String,String> findPassword(@PathVariable String memberEmail ,@RequestBody emailChkDto dto){
        Map result = new HashMap();
        // 이메일 전송 로직
        String to = dto.getEmail();
        String subject = "임시 비밀번호";

        String code = emailService.findPassword(to, subject);
        result.put("code",code);
        return result;
    }

    @PostMapping("/send-email")
    public Map<String ,String> sendEmail(@RequestBody emailChkDto emailRequest) {
        Map result = new HashMap();
        // 이메일 전송 로직
        String to = emailRequest.getEmail();
        String subject = "이메일 인증";
        String code = generateRandomCode();
        String body = "인증 코드: " + code;

        result.put("code",code);
        emailService.sendEmail(to, subject, body);
        return result;
    }

    private String generateRandomCode() {
        return java.util.UUID.randomUUID().toString();
    }


}

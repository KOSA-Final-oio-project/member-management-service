package com.oio.memberservice.service;

import com.oio.memberservice.dto.emailChkDto;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService{

    private final JavaMailSender javaMailSender;

    private final MemberService memberService;

    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        javaMailSender.send(message);
    }

    public String findPassword(String memberEmail,String subject) {
        SimpleMailMessage message = new SimpleMailMessage();
        String code = java.util.UUID.randomUUID().toString();
        String body = "임시 비밀번호 : "+ code;
        sendEmail(memberEmail,subject,body);

        memberService.resetPassword(memberEmail,code);

        return code;
    }
}

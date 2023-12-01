package com.oio.memberservice;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Properties;

@SpringBootApplication
@EnableDiscoveryClient
@EnableScheduling
public class MemberServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MemberServiceApplication.class, args);
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Bean
	public JavaMailSender javaMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost("smtp.naver.com");
		mailSender.setPort(465);  // 메일 서버의 포트 설정
		mailSender.setUsername("");
		mailSender.setPassword("");

		Properties props = mailSender.getJavaMailProperties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.transport.protocol","smtp" );
		props.put("mail.smtp.ssl.protocols", "TLSv1.2");  // 여기에 추가

		// 다른 설정들도 필요하다면 추가

		return mailSender;
	}
}

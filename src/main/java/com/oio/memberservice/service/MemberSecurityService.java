package com.oio.memberservice.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.oio.memberservice.entity.MemberEntity;
import com.oio.memberservice.repository.MemberRepository;
import com.oio.memberservice.status.MemberRole;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@Service
public class MemberSecurityService implements UserDetailsService {

    private final MemberRepository memberRepository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<MemberEntity> _siteUser = this.memberRepository.findByEmail(email);
        if (_siteUser.isEmpty()) {
            throw new UsernameNotFoundException("사용자를 찾을수 없습니다.");
        }
        MemberEntity siteUser = _siteUser.get();
        List<GrantedAuthority> authorities = new ArrayList<>();
        if ("admin".equals(email)) {
            authorities.add(new SimpleGrantedAuthority(MemberRole.ADMIN.getValue()));
        } else {
            authorities.add(new SimpleGrantedAuthority(MemberRole.USER.getValue()));
        }
        return new User(siteUser.getNickname(), siteUser.getPassword(), authorities);
    }

}

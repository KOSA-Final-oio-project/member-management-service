package com.oio.memberservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDto {
    private String email;
    private String nickname;
}

package com.oio.memberservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberResponseDto {

    private String email;
    private String name;
    private String nickname;
    private String profile;
}

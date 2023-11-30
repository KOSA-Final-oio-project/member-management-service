package com.oio.memberservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class memberUpdateDto {

    private String name;
    private String email;
    private String nickname;
    private String password;
    private String phoneNumber;
    private String profile;
}

package com.oio.memberservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ReportDto {

    private String category;
    private String content;
    private String reporterNickname;
    private String reportedNickname;

}

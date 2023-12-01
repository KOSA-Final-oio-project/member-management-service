package com.oio.memberservice.controller;

import com.oio.memberservice.dto.ReportDto;
import com.oio.memberservice.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member-service")
public class ReportController {

    private final ReportService reportService;

    @GetMapping("member/{memberNickname}/report")
    public ResponseEntity<String> reportMember(@PathVariable String memberNickname, @RequestBody ReportDto dto, Principal principal){
        reportService.createReport(memberNickname,dto,principal);
        return ResponseEntity.ok("신고 등록 완료");
    }
}

package com.oio.memberservice.controller;

import com.oio.memberservice.dto.ReportDto;
import com.oio.memberservice.service.ReportService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member-service")
public class ReportController {

    private final ReportService reportService;

    @PostMapping(value = "member/report",consumes = "multipart/form-data")
    public ResponseEntity<String> reportMember(@RequestPart List<MultipartFile> photos,  ReportDto dto){

        try {
            reportService.createReport(photos, dto);
        }catch (IOException e){
            e.printStackTrace();
        }
        return ResponseEntity.ok("신고 등록 완료");
    }
}

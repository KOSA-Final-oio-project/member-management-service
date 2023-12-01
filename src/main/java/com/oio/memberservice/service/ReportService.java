package com.oio.memberservice.service;

import com.oio.memberservice.dto.ReportDto;
import com.oio.memberservice.entity.ReportCategory;
import com.oio.memberservice.entity.ReportEntity;
import com.oio.memberservice.repository.ReportCategoryRepository;
import com.oio.memberservice.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ModelMapper mapper;

    private final ReportRepository reportRepository;
    private final ReportCategoryRepository reportCategoryRepository;

    public void createReport(String memberNickname, ReportDto dto, Principal principal) {
        ReportCategory reportCategory = reportCategoryRepository.findByName(dto.getCategory()).get();
        ReportEntity report = mapper.map(dto, ReportEntity.class);
        report.setReportCategory(reportCategory);
        report.setReportedNickname(memberNickname);
        report.setReporterNickname(principal.getName());

        reportRepository.save(report);
    }
}

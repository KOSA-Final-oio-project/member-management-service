package com.oio.memberservice.service;

import com.oio.memberservice.dto.ReportDto;
import com.oio.memberservice.entity.ReportCategory;
import com.oio.memberservice.entity.ReportEntity;
import com.oio.memberservice.entity.ReportPhoto;
import com.oio.memberservice.repository.ReportCategoryRepository;
import com.oio.memberservice.repository.ReportPhotoRepository;
import com.oio.memberservice.repository.ReportRepository;
import com.oio.memberservice.s3.S3Service;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ModelMapper mapper;

    private final ReportRepository reportRepository;
    private final ReportCategoryRepository reportCategoryRepository;

    private final ReportPhotoRepository reportPhotoRepository;

    private final S3Service s3service;

    @Transactional
    public void createReport(List<MultipartFile> photos, ReportDto dto) throws IOException {
        ReportCategory reportCategory = reportCategoryRepository.findByName(dto.getCategory()).get();
        ReportEntity report = mapper.map(dto, ReportEntity.class);

        report.setReportCategory(reportCategory);
        report.setReportedNickname(dto.getReportedNickname());
        report.setReporterNickname(dto.getReporterNickname());

        reportRepository.save(report);

        for(MultipartFile photo : photos){
            ReportPhoto reportPhoto = new ReportPhoto();
            String fileName = s3service.upload(photo);
            reportPhoto.updateImg(fileName);
            reportPhoto.updateReport(report);
            reportPhotoRepository.save(reportPhoto);
        }

    }
}

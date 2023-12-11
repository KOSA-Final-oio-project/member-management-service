package com.oio.memberservice.entity;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class ReportPhoto {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "REPORT_ID")
    private ReportEntity report;

    private String imgUrl;

    public void updateImg(String imgUrl){
        this.imgUrl = imgUrl;
    }

    public void updateReport(ReportEntity report){
        this.report = report;
    }
}

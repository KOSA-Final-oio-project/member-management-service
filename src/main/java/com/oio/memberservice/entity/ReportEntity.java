package com.oio.memberservice.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class ReportEntity {

    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REPORT_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "CATEGORY_ID")
    private ReportCategory reportCategory;

    private String reporterNickname;

    private String reportedNickname;

    private String content;

    @OneToMany(mappedBy = "report")
    private List<ReportPhoto> photos = new ArrayList<>();

}

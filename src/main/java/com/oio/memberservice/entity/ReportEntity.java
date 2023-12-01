package com.oio.memberservice.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class ReportEntity {

    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "CATEGORY_ID")
    private ReportCategory reportCategory;

    private String reporterNickname;

    private String reportedNickname;

    private String content;

    private String photo1;

    private String photo2;
    private String photo3;

}

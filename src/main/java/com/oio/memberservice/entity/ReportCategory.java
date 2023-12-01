package com.oio.memberservice.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class ReportCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CATEGORY_ID")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "reportCategory")
    private List<ReportEntity> reports = new ArrayList<>();

}

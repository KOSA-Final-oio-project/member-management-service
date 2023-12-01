package com.oio.memberservice.repository;

import com.oio.memberservice.entity.ReportCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReportCategoryRepository extends JpaRepository<ReportCategory,Long> {
    Optional<ReportCategory> findByName(String category);
}

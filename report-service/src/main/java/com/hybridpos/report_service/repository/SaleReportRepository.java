package com.hybridpos.report_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hybridpos.report_service.entity.SaleReport;

public interface SaleReportRepository extends JpaRepository<SaleReport, Long> {

}
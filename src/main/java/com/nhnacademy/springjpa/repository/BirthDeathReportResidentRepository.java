package com.nhnacademy.springjpa.repository;

import com.nhnacademy.springjpa.entity.BirthDeathReportResident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BirthDeathReportResidentRepository
        extends JpaRepository<BirthDeathReportResident, BirthDeathReportResident.Pk> {

    @Query("select report from BirthDeathReportResident report where report.pk.residentSerialNumber = :serialNum and report.pk.birthDeathTypeCode = :type")
    BirthDeathReportResident findReport(@Param("serialNum") int serialNum, @Param("type") String type);
}

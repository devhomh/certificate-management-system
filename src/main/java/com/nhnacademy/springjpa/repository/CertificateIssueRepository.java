package com.nhnacademy.springjpa.repository;

import com.nhnacademy.springjpa.entity.CertificateIssue;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CertificateIssueRepository extends JpaRepository<CertificateIssue, Long> {
    CertificateIssue findByResident_residentSerialNumberAndCertificateTypeCode(int serialNum, String type);
    List<CertificateIssue> findByResident_residentSerialNumber(int serialNum);
}

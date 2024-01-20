package com.nhnacademy.springjpa.service;

import com.nhnacademy.springjpa.entity.CertificateIssue;

public interface CertificateIssueService {
    CertificateIssue getCertificate(int serialNumber, String type);
    CertificateIssue createCertificate(int serialNumber, String type);
}

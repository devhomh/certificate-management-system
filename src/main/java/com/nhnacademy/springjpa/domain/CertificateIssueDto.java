package com.nhnacademy.springjpa.domain;

import java.time.LocalDate;

public interface CertificateIssueDto {
    Long getCertificateConfirmationNumber();

    LocalDate getCertificateIssueDate();
}

package com.nhnacademy.springjpa.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ResidentCertificateInfo {
    String name;
    int residentSerialNumber;
    boolean residentRegister;
    boolean familyRelationship;
    boolean birthReport;
    boolean deathReport;
}

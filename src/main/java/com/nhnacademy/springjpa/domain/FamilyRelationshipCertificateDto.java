package com.nhnacademy.springjpa.domain;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FamilyRelationshipCertificateDto {
    String familyRelationshipCode;
    Resident resident;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class Resident{
        String name;
        String residentRegistrationNumber;
        String genderCode;
        LocalDateTime birthDate;
    }
}

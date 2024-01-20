package com.nhnacademy.springjpa.service;

import com.nhnacademy.springjpa.domain.FamilyRelationshipCertificateDto;
import java.util.List;

public interface FamilyRelationshipService {
    List<FamilyRelationshipCertificateDto> getFamilyRelationship(int serialNum);
}

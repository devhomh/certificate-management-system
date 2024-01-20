package com.nhnacademy.springjpa.repository;

import com.nhnacademy.springjpa.domain.FamilyRelationshipCertificateDto;
import java.util.List;

public interface FamilyRelationshipRepositoryCustom {
    List<FamilyRelationshipCertificateDto> getFamilyRelationshipInfo(int serialNum);
}

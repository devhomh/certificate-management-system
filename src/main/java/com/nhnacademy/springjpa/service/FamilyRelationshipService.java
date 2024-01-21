package com.nhnacademy.springjpa.service;

import com.nhnacademy.springjpa.domain.FamilyRelationshipCertificateDto;
import com.nhnacademy.springjpa.domain.RelationshipModifyRequest;
import com.nhnacademy.springjpa.domain.RelationshipRegisterRequest;
import com.nhnacademy.springjpa.entity.FamilyRelationship;
import java.util.List;

public interface FamilyRelationshipService {
    List<FamilyRelationshipCertificateDto> getFamilyRelationship(int serialNum);

    FamilyRelationship registerRelationship(int serialNum, RelationshipRegisterRequest request);
    void modifyRelationship(int serialNumber, int familySerialNumber, RelationshipModifyRequest request);
    void deleteRelationship(int serialNumber, int familySerialNumber);
}

package com.nhnacademy.springjpa.service;

import com.nhnacademy.springjpa.domain.FamilyRelationshipCertificateDto;
import com.nhnacademy.springjpa.repository.FamilyRelationshipRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("FamilyRelationshipService")
public class FamilyRelationshipServiceImpl implements FamilyRelationshipService{
    private final FamilyRelationshipRepository familyRelationshipRepository;

    public FamilyRelationshipServiceImpl(FamilyRelationshipRepository familyRelationshipRepository) {
        this.familyRelationshipRepository = familyRelationshipRepository;
    }

    @Transactional
    @Override
    public List<FamilyRelationshipCertificateDto> getFamilyRelationship(int serialNum) {
        return familyRelationshipRepository.getFamilyRelationshipInfo(serialNum);
    }
}

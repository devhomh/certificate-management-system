package com.nhnacademy.springjpa.service;

import com.nhnacademy.springjpa.domain.FamilyRelationshipCertificateDto;
import com.nhnacademy.springjpa.domain.RelationshipModifyRequest;
import com.nhnacademy.springjpa.domain.RelationshipRegisterRequest;
import com.nhnacademy.springjpa.entity.FamilyRelationship;
import com.nhnacademy.springjpa.entity.Resident;
import com.nhnacademy.springjpa.exception.CannotRelationshipException;
import com.nhnacademy.springjpa.exception.RelationshipAlreadyExistsException;
import com.nhnacademy.springjpa.exception.RelationshipNotFoundException;
import com.nhnacademy.springjpa.exception.ResidentNotFoundException;
import com.nhnacademy.springjpa.repository.FamilyRelationshipRepository;
import com.nhnacademy.springjpa.repository.ResidentRepository;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("familyRelationshipService")
public class FamilyRelationshipServiceImpl implements FamilyRelationshipService{
    private final FamilyRelationshipRepository familyRelationshipRepository;
    private final ResidentRepository residentRepository;

    public FamilyRelationshipServiceImpl(FamilyRelationshipRepository familyRelationshipRepository,
                                         ResidentRepository residentRepository) {
        this.familyRelationshipRepository = familyRelationshipRepository;
        this.residentRepository = residentRepository;
    }

    @Transactional
    @Override
    public List<FamilyRelationshipCertificateDto> getFamilyRelationship(int serialNum) {
        return familyRelationshipRepository.getFamilyRelationshipInfo(serialNum);
    }

    public String conversionRelationshipCode(String relationshipCode){
        if (Objects.equals(relationshipCode, "father")) {
            return "부";
        } else if (Objects.equals(relationshipCode, "mother")) {
            return "모";
        } else if (Objects.equals(relationshipCode, "spouse")) {
            return "배우자";
        } else {
            return "자녀";
        }
    }

    @Transactional
    @Override
    public FamilyRelationship registerRelationship(int serialNum, RelationshipRegisterRequest request) {
        int familySerialNumber = request.getFamilySerialNumber();

        if(!residentRepository.existsById(familySerialNumber)){
           throw new ResidentNotFoundException();
        }

        Optional<Resident> resident = residentRepository.findById(serialNum);
        if(resident.isEmpty()){
            throw new ResidentNotFoundException();
        }

        if(serialNum == familySerialNumber){
            throw new CannotRelationshipException();
        }

        List<FamilyRelationship> relationshipList = familyRelationshipRepository.getFamilyRelationshipList(serialNum);

        if(Objects.nonNull(relationshipList) && !relationshipList.isEmpty()){
            relationshipList.forEach(relationship -> {
                if(Objects.equals(relationship.getFamilyRelationshipCode(),
                        conversionRelationshipCode(request.getRelationship()))){
                    throw new RelationshipAlreadyExistsException();
                }
            });
        }

        FamilyRelationship familyRelationship = new FamilyRelationship();

        familyRelationship.setResident(resident.get());
        familyRelationship.getPk().setFamilyResidentSerialNumber(familySerialNumber);
        familyRelationship.getPk().setBaseResidentSerialNumber(serialNum);
        familyRelationship.setFamilyRelationshipCode(conversionRelationshipCode(request.getRelationship()));

        familyRelationshipRepository.save(familyRelationship);

        return familyRelationship;
    }

    @Transactional
    @Override
    public void modifyRelationship(int serialNumber, int familySerialNumber, RelationshipModifyRequest request) {
        if(serialNumber == familySerialNumber){
            throw new CannotRelationshipException();
        }

        FamilyRelationship relationship = familyRelationshipRepository
                .getByPk_BaseResidentSerialNumberAndPk_FamilyResidentSerialNumber(serialNumber, familySerialNumber);

        if(Objects.isNull(relationship)){
            throw new RelationshipNotFoundException();
        }

        relationship.setFamilyRelationshipCode(conversionRelationshipCode(request.getRelationship()));

        familyRelationshipRepository.save(relationship);
    }

    @Transactional
    @Override
    public void deleteRelationship(int serialNumber, int familySerialNumber) {
        if(serialNumber == familySerialNumber){
            throw new CannotRelationshipException();
        }

        FamilyRelationship relationship = familyRelationshipRepository
                .getByPk_BaseResidentSerialNumberAndPk_FamilyResidentSerialNumber(serialNumber, familySerialNumber);

        if(Objects.isNull(relationship)){
            throw new RelationshipNotFoundException();
        }

        familyRelationshipRepository.delete(relationship);
    }
}

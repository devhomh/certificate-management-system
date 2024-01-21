package com.nhnacademy.springjpa.service;

import com.nhnacademy.springjpa.domain.HouseholdCompositionResidentDto;
import com.nhnacademy.springjpa.domain.RegistrantDto;
import com.nhnacademy.springjpa.domain.ResidentDto;
import com.nhnacademy.springjpa.domain.ResidentModifyRequest;
import com.nhnacademy.springjpa.domain.ResidentRegisterRequest;
import com.nhnacademy.springjpa.entity.BirthDeathReportResident;
import com.nhnacademy.springjpa.entity.CertificateIssue;
import com.nhnacademy.springjpa.entity.FamilyRelationship;
import com.nhnacademy.springjpa.entity.Household;
import com.nhnacademy.springjpa.entity.Resident;
import com.nhnacademy.springjpa.exception.CannotDeleteResidentException;
import com.nhnacademy.springjpa.exception.ResidentAlreadyExistsException;
import com.nhnacademy.springjpa.exception.ResidentNotFoundException;
import com.nhnacademy.springjpa.repository.BirthDeathReportResidentRepository;
import com.nhnacademy.springjpa.repository.CertificateIssueRepository;
import com.nhnacademy.springjpa.repository.FamilyRelationshipRepository;
import com.nhnacademy.springjpa.repository.HouseholdCompositionResidentRepository;
import com.nhnacademy.springjpa.repository.HouseholdRepository;
import com.nhnacademy.springjpa.repository.ResidentRepository;
import java.util.List;
import java.util.Objects;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("residentService")
public class ResidentServiceImpl implements ResidentService{
    private final ResidentRepository residentRepository;

    private final HouseholdCompositionResidentRepository householdCompositionResidentRepository;

    private final CertificateIssueRepository certificateIssueRepository;

    private final HouseholdRepository householdRepository;

    private final FamilyRelationshipRepository familyRelationshipRepository;

    private final BirthDeathReportResidentRepository birthDeathReportResidentRepository;

    public ResidentServiceImpl(ResidentRepository residentRepository,
                               HouseholdCompositionResidentRepository householdCompositionResidentRepository,
                               CertificateIssueRepository certificateIssueRepository,
                               HouseholdRepository householdRepository,
                               FamilyRelationshipRepository familyRelationshipRepository,
                               BirthDeathReportResidentRepository birthDeathReportResidentRepository) {

        this.residentRepository = residentRepository;
        this.householdCompositionResidentRepository = householdCompositionResidentRepository;
        this.certificateIssueRepository = certificateIssueRepository;
        this.householdRepository = householdRepository;
        this.familyRelationshipRepository = familyRelationshipRepository;
        this.birthDeathReportResidentRepository = birthDeathReportResidentRepository;
    }

    @Transactional
    @Override
    public Page<ResidentDto> getAllBy(Pageable pageable) {
        return residentRepository.getAllBy(pageable);
    }

    private String hideBackNumber(String number){
        String frontPart = number.substring(0, 6);
        String backPart = number.substring(7)
                .replaceAll("\\d", "*");
        return frontPart + "-" + backPart;
    }

    @Transactional
    @Override
    public Resident getResident(int serialNum){
        return residentRepository.findById(serialNum)
                .orElseThrow(ResidentNotFoundException::new);
    }

    @Transactional
    @Override
    public List<HouseholdCompositionResidentDto> getHouseholdCompositionResident(int serialNum){
        int householdSerialNum = householdCompositionResidentRepository.getHouseholdSerialNum(serialNum);
        List<HouseholdCompositionResidentDto> result = residentRepository
                .getHouseholdCompositionResident(householdSerialNum);

        for (HouseholdCompositionResidentDto dto : result) {
            String modifiedNum = hideBackNumber(dto.getResidentRegistrationNumber());
            dto.setResidentRegistrationNumber(modifiedNum);
        }
        return result;
    }

    @Transactional
    @Override
    public HouseholdCompositionResidentDto getHeadOfHousehold(int serialNum) {
        return getHouseholdCompositionResident(serialNum)
                .stream()
                .filter(resident -> resident.getHouseholdCompositionChangeReasonCode().equals("세대분리"))
                .findFirst()
                .orElseThrow();
    }

    @Transactional
    @Override
    public RegistrantDto findRegistrant(int serialNum) {
        return residentRepository.findRegistrant(serialNum);
    }

    @Transactional
    @Override
    public void deleteResident(int serialNum) {
        List<CertificateIssue> certificateIssueList = certificateIssueRepository.findByResident_residentSerialNumber(serialNum);
        if(Objects.nonNull(certificateIssueList) && !certificateIssueList.isEmpty()){
        certificateIssueRepository.deleteAll(certificateIssueList);
        }

        Household household = householdRepository.findByResident_ResidentSerialNumber(serialNum);
        if(Objects.nonNull(household)){
            if(Objects.equals(household.getHouseholdCompositionReasonCode(), "세대분리")) {
                throw new CannotDeleteResidentException();
            }
            householdRepository.delete(household);
        }

        List<FamilyRelationship> familyRelationshipList = familyRelationshipRepository
                .findByPk_FamilyResidentSerialNumber(serialNum);
        if(Objects.nonNull(familyRelationshipList) && !familyRelationshipList.isEmpty()){
            familyRelationshipRepository.deleteAll(familyRelationshipList);
        }

        List<BirthDeathReportResident> birthDeathReportResidentList = birthDeathReportResidentRepository
                .findByResident_ResidentSerialNumber(serialNum);
        if(Objects.nonNull(birthDeathReportResidentList) && !birthDeathReportResidentList.isEmpty()){
            birthDeathReportResidentRepository.deleteAll(birthDeathReportResidentList);
        }

        residentRepository.deleteById(serialNum);
    }

    @Transactional
    @Override
    public Resident registerResident(ResidentRegisterRequest resident) {
        int residentId = resident.getResidentSerialNumber();
        if(residentRepository.existsById(residentId)){
            throw new ResidentAlreadyExistsException();
        }

        Resident newResident = new Resident();
        newResident.setResidentSerialNumber(resident.getResidentSerialNumber());
        newResident.setName(resident.getName());
        newResident.setResidentRegistrationNumber(resident.getResidentRegistrationNumber());
        newResident.setGenderCode(resident.getGenderCode());
        newResident.setBirthDate(resident.getBirthDate());
        newResident.setBirthPlaceCode(resident.getBirthPlaceCode());
        newResident.setRegistrationBaseAddress(resident.getRegistrationBaseAddress());
        newResident.setDeathDate(resident.getDeathDate());
        newResident.setDeathPlaceCode(resident.getDeathPlaceCode());
        newResident.setDeathPlaceAddress(resident.getDeathPlaceAddress());

        residentRepository.save(newResident);

        return newResident;
    }

    @Transactional
    @Override
    public void modifyResident(int serialNum, ResidentModifyRequest resident) {
        Resident modifiedResident = residentRepository.findById(serialNum)
                .orElseThrow(ResidentNotFoundException::new);

        modifiedResident.setName(resident.getName());
        modifiedResident.setResidentRegistrationNumber(resident.getResidentRegistrationNumber());
        modifiedResident.setGenderCode(resident.getGenderCode());
        modifiedResident.setBirthDate(resident.getBirthDate());
        modifiedResident.setBirthPlaceCode(resident.getBirthPlaceCode());
        modifiedResident.setRegistrationBaseAddress(resident.getRegistrationBaseAddress());
        modifiedResident.setDeathDate(resident.getDeathDate());
        modifiedResident.setDeathPlaceCode(resident.getDeathPlaceCode());
        modifiedResident.setDeathPlaceAddress(resident.getDeathPlaceAddress());

        residentRepository.save(modifiedResident);
    }
}

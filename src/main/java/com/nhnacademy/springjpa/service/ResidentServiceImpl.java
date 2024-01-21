package com.nhnacademy.springjpa.service;

import com.nhnacademy.springjpa.domain.HouseholdCompositionResidentDto;
import com.nhnacademy.springjpa.domain.RegistrantDto;
import com.nhnacademy.springjpa.domain.ResidentDto;
import com.nhnacademy.springjpa.entity.Resident;
import com.nhnacademy.springjpa.exception.ResidentNotFoundException;
import com.nhnacademy.springjpa.repository.HouseholdCompositionResidentRepository;
import com.nhnacademy.springjpa.repository.ResidentRepository;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service("residentService")
public class ResidentServiceImpl implements ResidentService{
    private final ResidentRepository residentRepository;

    private final HouseholdCompositionResidentRepository householdCompositionResidentRepository;

    public ResidentServiceImpl(ResidentRepository residentRepository,
                               HouseholdCompositionResidentRepository householdCompositionResidentRepository) {
        this.residentRepository = residentRepository;
        this.householdCompositionResidentRepository = householdCompositionResidentRepository;
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
}

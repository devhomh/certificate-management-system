package com.nhnacademy.springjpa.service;

import com.nhnacademy.springjpa.domain.HouseholdCompositionResidentDto;
import com.nhnacademy.springjpa.domain.RegistrantDto;
import com.nhnacademy.springjpa.domain.ResidentDto;
import com.nhnacademy.springjpa.domain.ResidentModifyRequest;
import com.nhnacademy.springjpa.domain.ResidentRegisterRequest;
import com.nhnacademy.springjpa.entity.Resident;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ResidentService {
    Resident getResident(int serialNum);
    Page<ResidentDto> getAllBy(Pageable pageable);
    List<HouseholdCompositionResidentDto> getHouseholdCompositionResident(int serialNum);

    HouseholdCompositionResidentDto getHeadOfHousehold(int serialNum);

    RegistrantDto findRegistrant(int serialNum);

    void deleteResident(int serialNum);

    Resident registerResident(ResidentRegisterRequest resident);

    void modifyResident(int serialNum, ResidentModifyRequest resident);
}

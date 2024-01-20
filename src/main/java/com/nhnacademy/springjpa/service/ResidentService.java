package com.nhnacademy.springjpa.service;

import com.nhnacademy.springjpa.domain.HouseholdCompositionResidentDto;
import com.nhnacademy.springjpa.domain.ResidentDto;
import com.nhnacademy.springjpa.entity.Resident;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ResidentService {
    Resident getResident(int serialNum);
    Page<ResidentDto> getAllBy(Pageable pageable);
    List<HouseholdCompositionResidentDto> getHouseholdCompositionResident(int serialNum);

    HouseholdCompositionResidentDto getHeadOfHousehold(int serialNum);
}

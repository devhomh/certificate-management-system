package com.nhnacademy.springjpa.repository;

import com.nhnacademy.springjpa.domain.HouseholdCompositionResidentDto;
import com.nhnacademy.springjpa.domain.RegistrantDto;
import java.util.List;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface ResidentRepositoryCustom {
    List<HouseholdCompositionResidentDto> getHouseholdCompositionResident(int householdSerialNumber);

    RegistrantDto findRegistrant(int serialNum);
}

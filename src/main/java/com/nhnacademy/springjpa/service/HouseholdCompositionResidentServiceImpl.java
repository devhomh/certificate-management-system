package com.nhnacademy.springjpa.service;

import com.nhnacademy.springjpa.entity.HouseholdCompositionResident;
import com.nhnacademy.springjpa.repository.HouseholdCompositionResidentRepository;
import org.springframework.stereotype.Service;

@Service("householdCompositionResidentService")
public class HouseholdCompositionResidentServiceImpl
        implements HouseholdCompositionResidentService {
    private final HouseholdCompositionResidentRepository householdCompositionResidentRepository;

    public HouseholdCompositionResidentServiceImpl(
            HouseholdCompositionResidentRepository householdCompositionResidentRepository) {
        this.householdCompositionResidentRepository = householdCompositionResidentRepository;
    }

    @Override
    public HouseholdCompositionResident getHouseholdCompositionResident(int serialNum) {
        return householdCompositionResidentRepository.
                findByResident_ResidentSerialNumber(serialNum);
    }
}

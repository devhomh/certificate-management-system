package com.nhnacademy.springjpa.service;

import com.nhnacademy.springjpa.repository.HouseholdRepository;
import org.springframework.stereotype.Service;

@Service("householdService")
public class HouseholdServiceImpl implements HouseholdService{
    private final HouseholdRepository householdRepository;

    public HouseholdServiceImpl(HouseholdRepository householdRepository) {
        this.householdRepository = householdRepository;
    }

    @Override
    public String getCurrentAddress(int serialNum) {
        return householdRepository.getCurrentAddress(serialNum);
    }
}

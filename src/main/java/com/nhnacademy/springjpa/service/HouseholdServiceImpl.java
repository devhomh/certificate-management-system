package com.nhnacademy.springjpa.service;

import com.nhnacademy.springjpa.repository.HouseholdRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("householdService")
public class HouseholdServiceImpl implements HouseholdService{
    private final HouseholdRepository householdRepository;

    public HouseholdServiceImpl(HouseholdRepository householdRepository) {
        this.householdRepository = householdRepository;
    }

    @Transactional
    @Override
    public String getCurrentAddress(int serialNum) {
        return householdRepository.getCurrentAddress(serialNum);
    }
}

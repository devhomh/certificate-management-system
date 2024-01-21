package com.nhnacademy.springjpa.repository;

import com.nhnacademy.springjpa.entity.HouseholdCompositionResident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface HouseholdCompositionResidentRepository
        extends JpaRepository<HouseholdCompositionResident, HouseholdCompositionResident.Pk> {
    @Query("select hcr.household.householdSerialNumber from HouseholdCompositionResident hcr where hcr.resident.residentSerialNumber = ?1")
    int getHouseholdSerialNum(int serialNum);

    HouseholdCompositionResident findByResident_ResidentSerialNumber(int serialNum);
}
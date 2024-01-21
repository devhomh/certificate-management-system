package com.nhnacademy.springjpa.repository;

import com.nhnacademy.springjpa.entity.Household;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface HouseholdRepository
        extends JpaRepository<Household, Integer>, HouseholdRepositoryCustom{
    @Query("select h.householdSerialNumber from Household h where h.resident.residentSerialNumber = ?1")
    int getHouseholdSerialNum(int serialNum);

    Household findByResident_ResidentSerialNumber(int serialNum);
}

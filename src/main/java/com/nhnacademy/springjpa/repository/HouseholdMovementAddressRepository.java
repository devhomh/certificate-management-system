package com.nhnacademy.springjpa.repository;

import com.nhnacademy.springjpa.domain.HouseholdAddressDto;
import com.nhnacademy.springjpa.entity.HouseholdMovementAddress;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HouseholdMovementAddressRepository extends JpaRepository<HouseholdMovementAddress, HouseholdMovementAddress.Pk> {
    List<HouseholdAddressDto> getAllByPk_HouseholdSerialNumber(int serialNum);
}

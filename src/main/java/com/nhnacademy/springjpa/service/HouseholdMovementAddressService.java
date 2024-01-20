package com.nhnacademy.springjpa.service;

import com.nhnacademy.springjpa.domain.HouseholdAddressDto;
import java.util.List;

public interface HouseholdMovementAddressService {
    List<HouseholdAddressDto> getAllAddressInfo(int serialNum);
}

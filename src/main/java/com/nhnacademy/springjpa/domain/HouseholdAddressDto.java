package com.nhnacademy.springjpa.domain;

import com.nhnacademy.springjpa.entity.HouseholdMovementAddress;
import java.time.LocalDate;

public interface HouseholdAddressDto {
    HouseholdMovementAddress.Pk getPk();
    String getHouseMovementAddress();
    String getLastAddressYn();

    interface Pk{
        LocalDate getHouseMovementReportDate();
    }
}

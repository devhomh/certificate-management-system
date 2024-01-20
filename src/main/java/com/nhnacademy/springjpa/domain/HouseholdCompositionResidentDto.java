package com.nhnacademy.springjpa.domain;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class HouseholdCompositionResidentDto {
    String name;
    String residentRegistrationNumber;
    LocalDate reportDate;
    String householdRelationshipCode;
    String householdCompositionChangeReasonCode;
}

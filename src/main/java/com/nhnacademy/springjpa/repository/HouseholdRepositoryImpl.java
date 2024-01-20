package com.nhnacademy.springjpa.repository;

import com.nhnacademy.springjpa.entity.Household;
import com.nhnacademy.springjpa.entity.QHousehold;
import com.nhnacademy.springjpa.entity.QHouseholdCompositionResident;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class HouseholdRepositoryImpl
        extends QuerydslRepositorySupport
        implements HouseholdRepositoryCustom {
    public HouseholdRepositoryImpl() {
        super(Household.class);
    }

    @Override
    public String getCurrentAddress(int serialNum) {
        QHousehold household = QHousehold.household;
        QHouseholdCompositionResident compositionResident = QHouseholdCompositionResident.householdCompositionResident;

        return from(household)
                .select(household.currentHouseMovementAddress)
                .innerJoin(compositionResident)
                .where(compositionResident.pk.residentSerialNumber.eq(serialNum))
                .on(household.householdSerialNumber.eq(compositionResident.pk.householdSerialNumber))
                .fetchJoin()
                .fetch()
                .stream()
                .findFirst()
                .orElse(null);
    }
}

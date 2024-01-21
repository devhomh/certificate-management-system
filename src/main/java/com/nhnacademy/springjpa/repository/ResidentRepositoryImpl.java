package com.nhnacademy.springjpa.repository;

import com.nhnacademy.springjpa.domain.HouseholdCompositionResidentDto;
import com.nhnacademy.springjpa.domain.RegistrantDto;
import com.nhnacademy.springjpa.entity.QBirthDeathReportResident;
import com.nhnacademy.springjpa.entity.QHousehold;
import com.nhnacademy.springjpa.entity.QHouseholdCompositionResident;
import com.nhnacademy.springjpa.entity.QResident;
import com.nhnacademy.springjpa.entity.Resident;
import com.querydsl.core.types.Projections;
import java.util.List;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class ResidentRepositoryImpl extends QuerydslRepositorySupport implements ResidentRepositoryCustom{
    public ResidentRepositoryImpl() {
        super(Resident.class);
    }

    @Override
    public List<HouseholdCompositionResidentDto> getHouseholdCompositionResident(int householdSerialNumber) {
        QResident resident = QResident.resident;
        QHousehold household = QHousehold.household;
        QHouseholdCompositionResident composition = QHouseholdCompositionResident.householdCompositionResident;

        return from(resident)
                .select(Projections.constructor(HouseholdCompositionResidentDto.class,
                        resident.name,
                        resident.residentRegistrationNumber,
                        composition.reportDate,
                        composition.householdRelationshipCode,
                        composition.householdCompositionChangeReasonCode))
                .innerJoin(composition)
                .on(composition.resident.residentSerialNumber.eq(resident.residentSerialNumber))
                .innerJoin(household)
                .on(household.householdSerialNumber.eq(composition.household.householdSerialNumber))
                .where(household.householdSerialNumber.eq(householdSerialNumber))
                .fetchJoin()
                .fetch();
    }

    @Override
    public RegistrantDto findRegistrant(int serialNum) {
        QResident resident = QResident.resident;
        QBirthDeathReportResident report = QBirthDeathReportResident.birthDeathReportResident;
        return from(resident)
                .select(Projections.constructor(RegistrantDto.class,
                        resident.name,
                        resident.residentRegistrationNumber))
                .innerJoin(report)
                .on(resident.residentSerialNumber.eq(report.pk.reportResidentSerialNumber))
                .where(report.pk.residentSerialNumber.eq(serialNum))
                .fetchJoin()
                .fetch()
                .stream()
                .findFirst()
                .orElseThrow();
    }
}

package com.nhnacademy.springjpa.repository;

import com.nhnacademy.springjpa.domain.FamilyRelationshipCertificateDto;
import com.nhnacademy.springjpa.entity.FamilyRelationship;
import com.nhnacademy.springjpa.entity.QFamilyRelationship;
import com.nhnacademy.springjpa.entity.QResident;
import com.querydsl.core.types.Projections;
import java.util.List;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class FamilyRelationshipRepositoryImpl
        extends QuerydslRepositorySupport
        implements FamilyRelationshipRepositoryCustom{
    public FamilyRelationshipRepositoryImpl() {
        super(FamilyRelationship.class);
    }

    @Override
    public List<FamilyRelationshipCertificateDto> getFamilyRelationshipInfo(int serialNum) {
        QFamilyRelationship familyRelationship = QFamilyRelationship.familyRelationship;
        QResident resident = QResident.resident;

        return from(familyRelationship)
                .select(Projections.constructor(FamilyRelationshipCertificateDto.class,
                        familyRelationship.familyRelationshipCode,
                        Projections.constructor(FamilyRelationshipCertificateDto.Resident.class,
                                resident.name,
                                resident.residentRegistrationNumber,
                                resident.genderCode,
                                resident.birthDate)))
                .leftJoin(resident)
                .on(familyRelationship.pk.familyResidentSerialNumber.eq(resident.residentSerialNumber))
                .where(familyRelationship.pk.baseResidentSerialNumber.eq(serialNum))
                .fetchJoin()
                .fetch();
    }
}

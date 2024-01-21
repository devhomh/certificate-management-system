package com.nhnacademy.springjpa.repository;

import com.nhnacademy.springjpa.entity.FamilyRelationship;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FamilyRelationshipRepository
        extends JpaRepository<FamilyRelationship, FamilyRelationship.Pk>, FamilyRelationshipRepositoryCustom{
    List<FamilyRelationship> findByPk_FamilyResidentSerialNumber(int serialNum);

    @Query("select f from FamilyRelationship f where f.pk.baseResidentSerialNumber = ?1")
    List<FamilyRelationship> getFamilyRelationshipList(int residentSerialNum);

    FamilyRelationship getByPk_BaseResidentSerialNumberAndPk_FamilyResidentSerialNumber(int serialNumber, int familySerialNumber);
}
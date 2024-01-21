package com.nhnacademy.springjpa.repository;

import com.nhnacademy.springjpa.entity.FamilyRelationship;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FamilyRelationshipRepository
        extends JpaRepository<FamilyRelationship, FamilyRelationship.Pk>, FamilyRelationshipRepositoryCustom{
    List<FamilyRelationship> findByPk_FamilyResidentSerialNumber(int serialNum);
}

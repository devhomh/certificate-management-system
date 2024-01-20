package com.nhnacademy.springjpa.repository;

import com.nhnacademy.springjpa.entity.FamilyRelationship;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FamilyRelationshipRepository
        extends JpaRepository<FamilyRelationship, FamilyRelationship.Pk>, FamilyRelationshipRepositoryCustom{

}

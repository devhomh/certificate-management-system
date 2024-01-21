package com.nhnacademy.springjpa.repository;

import com.nhnacademy.springjpa.domain.ResidentDto;
import com.nhnacademy.springjpa.entity.Resident;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResidentRepository extends JpaRepository<Resident, Integer>, ResidentRepositoryCustom {
    Integer findResidentSerialNumberByName(String name);
    Page<ResidentDto> getAllBy(Pageable pageable);
}
package com.nhnacademy.springjpa.service;

import com.nhnacademy.springjpa.entity.BirthDeathReportResident;
import com.nhnacademy.springjpa.repository.BirthDeathReportResidentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("birthDeathReportResidentService")
public class BirthDeathReportResidentServiceImpl implements BirthDeathReportResidentService {
    private final BirthDeathReportResidentRepository birthDeathReportResidentRepository;

    public BirthDeathReportResidentServiceImpl(BirthDeathReportResidentRepository birthDeathReportResidentRepository) {
        this.birthDeathReportResidentRepository = birthDeathReportResidentRepository;
    }

    @Transactional
    @Override
    public BirthDeathReportResident findReport(int serialNum, String type) {
        return birthDeathReportResidentRepository.findReport(serialNum, type);
    }
}

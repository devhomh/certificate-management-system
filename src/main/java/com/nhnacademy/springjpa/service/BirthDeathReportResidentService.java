package com.nhnacademy.springjpa.service;

import com.nhnacademy.springjpa.entity.BirthDeathReportResident;

public interface BirthDeathReportResidentService {
    BirthDeathReportResident findReport(int serialNum, String type);
}

package com.nhnacademy.springjpa.controller;

import com.nhnacademy.springjpa.domain.HouseholdAddressDto;
import com.nhnacademy.springjpa.domain.HouseholdCompositionResidentDto;
import com.nhnacademy.springjpa.entity.CertificateIssue;
import com.nhnacademy.springjpa.service.CertificateIssueService;
import com.nhnacademy.springjpa.service.HouseholdMovementAddressService;
import com.nhnacademy.springjpa.service.ResidentService;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class CertificateController {
    private final ResidentService residentService;
    private final HouseholdMovementAddressService householdMovementAddressService;
    private final CertificateIssueService certificateIssueService;

    public CertificateController(ResidentService residentService,
                                 HouseholdMovementAddressService householdMovementAddressService,
                                 CertificateIssueService certificateIssueService) {
        this.residentService = residentService;
        this.householdMovementAddressService = householdMovementAddressService;
        this.certificateIssueService = certificateIssueService;
    }

    @GetMapping("/resident-register/{serialNumber}")
    public String residentRegister(@PathVariable("serialNumber") int serialNum,
                                          ModelMap modelMap){
        List<HouseholdCompositionResidentDto> compositionList
                = residentService.getHouseholdCompositionResident(serialNum);
        modelMap.addAttribute("compositionList", compositionList);


        List<HouseholdAddressDto> addressList
                = householdMovementAddressService.getAllAddressInfo(serialNum);
        modelMap.addAttribute("addressInfoList", addressList);

        HouseholdCompositionResidentDto headOfHousehold
                = residentService.getHeadOfHousehold(serialNum);
        modelMap.addAttribute("headOfHousehold", headOfHousehold);

        CertificateIssue certificate = certificateIssueService.getCertificate(serialNum, "주민등록등본");
        certificate = Objects.nonNull(certificate)
                ? certificate
                : certificateIssueService.createCertificate(serialNum, "주민등록등본");
        modelMap.addAttribute("certificate", certificate);

        return "thymeleaf/residentRegister";
    }
}

package com.nhnacademy.springjpa.controller;

import com.nhnacademy.springjpa.domain.FamilyRelationshipCertificateDto;
import com.nhnacademy.springjpa.domain.HouseholdAddressDto;
import com.nhnacademy.springjpa.domain.HouseholdCompositionResidentDto;
import com.nhnacademy.springjpa.domain.RegistrantDto;
import com.nhnacademy.springjpa.entity.BirthDeathReportResident;
import com.nhnacademy.springjpa.entity.CertificateIssue;
import com.nhnacademy.springjpa.entity.Resident;
import com.nhnacademy.springjpa.service.BirthDeathReportResidentService;
import com.nhnacademy.springjpa.service.CertificateIssueService;
import com.nhnacademy.springjpa.service.FamilyRelationshipService;
import com.nhnacademy.springjpa.service.HouseholdMovementAddressService;
import com.nhnacademy.springjpa.service.HouseholdService;
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
    private final FamilyRelationshipService familyRelationshipService;
    private final CertificateIssueService certificateIssueService;
    private final HouseholdService householdService;
    private final BirthDeathReportResidentService birthDeathReportResidentService;

    public CertificateController(ResidentService residentService,
                                 HouseholdMovementAddressService householdMovementAddressService,
                                 FamilyRelationshipService familyRelationshipService,
                                 CertificateIssueService certificateIssueService, HouseholdService householdService,
                                 BirthDeathReportResidentService birthDeathReportResidentService) {
        this.residentService = residentService;
        this.householdMovementAddressService = householdMovementAddressService;
        this.familyRelationshipService = familyRelationshipService;
        this.certificateIssueService = certificateIssueService;
        this.householdService = householdService;
        this.birthDeathReportResidentService = birthDeathReportResidentService;
    }

    private void addCertificateAttribute(int serialNum, ModelMap modelMap, String type){
        CertificateIssue certificate = certificateIssueService.getCertificate(serialNum, type);
        certificate = Objects.nonNull(certificate)
                ? certificate
                : certificateIssueService.createCertificate(serialNum, type);
        modelMap.addAttribute("certificate", certificate);
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

        addCertificateAttribute(serialNum, modelMap, "주민등록등본");

        return "thymeleaf/residentRegister";
    }

    @GetMapping("/family-relationship/{serialNumber}")
    public String familyRelationship(@PathVariable("serialNumber") int serialNum,
                                     ModelMap modelMap) {
        String currentAddress = householdService.getCurrentAddress(serialNum);
        modelMap.addAttribute("currentAddress", currentAddress);

        List<FamilyRelationshipCertificateDto> relationshipInfoList =
                familyRelationshipService.getFamilyRelationship(serialNum);
        modelMap.addAttribute("relationshipInfoList", relationshipInfoList);

        Resident own = residentService.getResident(serialNum);
        modelMap.addAttribute("own", own);

        addCertificateAttribute(serialNum, modelMap, "가족관계증명서");

        return "thymeleaf/familyRelationship";
    }

    @GetMapping("/birth-report/{serialNumber}")
    public String birthReport(@PathVariable("serialNumber") int serialNum,
                              ModelMap modelMap){

        BirthDeathReportResident info =
                birthDeathReportResidentService.findReport(serialNum, "출생");
        modelMap.addAttribute("info", info);


        Resident own = residentService.getResident(serialNum);
        modelMap.addAttribute("own", own);

        String currentAddress = householdService.getCurrentAddress(serialNum);
        modelMap.addAttribute("currentAddress", currentAddress);

        RegistrantDto registrant = residentService.findRegistrant(serialNum);
        modelMap.addAttribute("registrant", registrant);

        List<FamilyRelationshipCertificateDto> parentInfo =
                familyRelationshipService.getFamilyRelationship(serialNum);
        modelMap.addAttribute("parentInfo", parentInfo);

        return "thymeleaf/birthReport";

    }

    @GetMapping("/death-report/{serialNumber}")
    public String deathReport(@PathVariable("serialNumber") int serialNum,
                              ModelMap modelMap){
        BirthDeathReportResident info =
                birthDeathReportResidentService.findReport(serialNum, "사망");
        modelMap.addAttribute("info", info);

        Resident own = residentService.getResident(serialNum);
        modelMap.addAttribute("own", own);

        RegistrantDto registrant = residentService.findRegistrant(serialNum);
        modelMap.addAttribute("registrant", registrant);

        return "thymeleaf/deathReport";
    }
}

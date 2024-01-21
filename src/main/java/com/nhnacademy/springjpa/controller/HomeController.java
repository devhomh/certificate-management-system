package com.nhnacademy.springjpa.controller;

import com.nhnacademy.springjpa.domain.CreatableCertificate;
import com.nhnacademy.springjpa.domain.FamilyRelationshipCertificateDto;
import com.nhnacademy.springjpa.domain.ResidentDto;
import com.nhnacademy.springjpa.service.BirthDeathReportResidentService;
import com.nhnacademy.springjpa.service.FamilyRelationshipService;
import com.nhnacademy.springjpa.service.HouseholdCompositionResidentService;
import com.nhnacademy.springjpa.service.ResidentServiceImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    private final ResidentServiceImpl residentService;
    private final HouseholdCompositionResidentService householdCompositionResidentService;
    private final FamilyRelationshipService familyRelationshipService;

    private final BirthDeathReportResidentService birthDeathReportResidentService;

    public HomeController(ResidentServiceImpl residentService,
                          HouseholdCompositionResidentService householdCompositionResidentService,
                          FamilyRelationshipService familyRelationshipService,
                          BirthDeathReportResidentService birthDeathReportResidentService) {
        this.residentService = residentService;
        this.householdCompositionResidentService = householdCompositionResidentService;
        this.familyRelationshipService = familyRelationshipService;
        this.birthDeathReportResidentService = birthDeathReportResidentService;
    }

    @GetMapping("/")
    public String home(@PageableDefault(size = 3) Pageable pageable, ModelMap modelMap){
        Page<ResidentDto> pageResult = residentService.getAllBy(pageable);
        List<ResidentDto> residentDtoList = pageResult.getContent();
        List<CreatableCertificate> residentList = new ArrayList<>();
        for (ResidentDto dto : residentDtoList) {
            CreatableCertificate creatableCertificate = new CreatableCertificate();
            creatableCertificate.setName(dto.getName());

            int serialNum = dto.getResidentSerialNumber();
            creatableCertificate.setResidentSerialNumber(serialNum);

            creatableCertificate.setResidentRegister(
                    Objects.nonNull(householdCompositionResidentService.getHouseholdCompositionResident(serialNum))
            );

            List<FamilyRelationshipCertificateDto> familyRelationshipList
                    = familyRelationshipService.getFamilyRelationship(serialNum);
            creatableCertificate.setFamilyRelationship(
                    Objects.nonNull(familyRelationshipList) && !familyRelationshipList.isEmpty()
            );

            creatableCertificate.setBirthReport(
                    Objects.nonNull(birthDeathReportResidentService.findReport(serialNum, "출생"))
            );

            creatableCertificate.setDeathReport(
                    Objects.nonNull(birthDeathReportResidentService.findReport(serialNum, "사망"))
            );

            residentList.add(creatableCertificate);
        }


        int currentPageNumber = pageResult.getNumber();
        org.springframework.data.domain.Pageable prevPage = currentPageNumber > 0
                ? pageable.previousOrFirst()
                : null;
        org.springframework.data.domain.Pageable nextPage = currentPageNumber < pageResult.getTotalPages() - 1
                ? pageable.next()
                : null;

        int totalPage = pageResult.getTotalPages() > 0 ? pageResult.getTotalPages() : 1;

        modelMap.addAttribute("residents", residentList);
        modelMap.addAttribute("totalPage", totalPage);
        modelMap.addAttribute("prevPage", prevPage);
        modelMap.addAttribute("nextPage", nextPage);

        return "thymeleaf/index";
    }
}

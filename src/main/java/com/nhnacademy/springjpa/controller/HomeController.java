package com.nhnacademy.springjpa.controller;

import com.nhnacademy.springjpa.domain.HouseholdCompositionResidentDto;
import com.nhnacademy.springjpa.domain.ResidentDto;
import com.nhnacademy.springjpa.service.ResidentServiceImpl;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    private final ResidentServiceImpl residentService;

    public HomeController(ResidentServiceImpl residentService) {
        this.residentService = residentService;
    }

    @GetMapping("/")
    public String home(@PageableDefault(size = 3) Pageable pageable, ModelMap modelMap){
        Page<ResidentDto> pageResult = residentService.getAllBy(pageable);

        int currentPageNumber = pageResult.getNumber();
        org.springframework.data.domain.Pageable prevPage = currentPageNumber > 0
                ? pageable.previousOrFirst()
                : null;
        org.springframework.data.domain.Pageable nextPage = currentPageNumber < pageResult.getTotalPages() - 1
                ? pageable.next()
                : null;

        int totalPage = pageResult.getTotalPages() > 0 ? pageResult.getTotalPages() : 1;

        modelMap.addAttribute("residents", pageResult.getContent());
        modelMap.addAttribute("totalPage", totalPage);
        modelMap.addAttribute("prevPage", prevPage);
        modelMap.addAttribute("nextPage", nextPage);

        return "thymeleaf/index";
    }
}

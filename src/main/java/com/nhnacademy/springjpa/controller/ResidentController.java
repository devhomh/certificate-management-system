package com.nhnacademy.springjpa.controller;

import com.nhnacademy.springjpa.service.ResidentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/residents")
public class ResidentController {
    private final ResidentService residentService;

    public ResidentController(ResidentService residentService) {
        this.residentService = residentService;
    }

    @PostMapping("/{serialNumber}/delete")
    public String deleteResident(@PathVariable("serialNumber") int serialNum){
        residentService.deleteResident(serialNum);

        return "redirect:/";
    }

}

package com.nhnacademy.springjpa.controller;

import com.nhnacademy.springjpa.exception.CannotDeleteResidentException;
import com.nhnacademy.springjpa.service.ResidentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/residents")
public class ResidentController {
    private final ResidentService residentService;

    public ResidentController(ResidentService residentService) {
        this.residentService = residentService;
    }

    @PostMapping("/{serialNumber}/delete")
    public String deleteResident(@PathVariable("serialNumber") int serialNum){
        try {
            residentService.deleteResident(serialNum);
        } catch(CannotDeleteResidentException exception){
            log.error("남은 가족이 있어 삭제가 불가능합니다.", exception);
        }

        return "redirect:/";
    }

}

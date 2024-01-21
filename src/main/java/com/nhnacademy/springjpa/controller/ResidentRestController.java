package com.nhnacademy.springjpa.controller;

import com.nhnacademy.springjpa.domain.ResidentModifyRequest;
import com.nhnacademy.springjpa.domain.ResidentRegisterRequest;
import com.nhnacademy.springjpa.service.ResidentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/residents")
public class ResidentRestController {
    private final ResidentService residentService;

    public ResidentRestController(ResidentService residentService) {
        this.residentService = residentService;
    }

    @PostMapping
    public ResponseEntity<String> registerResident(@RequestBody ResidentRegisterRequest resident){
        residentService.registerResident(resident);

        return ResponseEntity.status(HttpStatus.CREATED).body("Registerd");
    }

    @PutMapping("/{serialNumber}")
    public ResponseEntity<String> modifyResident(@PathVariable("serialNumber") int serialNum,
                                                 @RequestBody ResidentModifyRequest resident){
        residentService.modifyResident(serialNum, resident);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Modified");
    }
}

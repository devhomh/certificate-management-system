package com.nhnacademy.springjpa.controller;

import com.nhnacademy.springjpa.domain.RelationshipModifyRequest;
import com.nhnacademy.springjpa.domain.RelationshipRegisterRequest;
import com.nhnacademy.springjpa.domain.ResidentModifyRequest;
import com.nhnacademy.springjpa.domain.ResidentRegisterRequest;
import com.nhnacademy.springjpa.service.FamilyRelationshipService;
import com.nhnacademy.springjpa.service.ResidentService;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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

    private final FamilyRelationshipService familyRelationshipService;

    public ResidentRestController(ResidentService residentService,
                                  FamilyRelationshipService familyRelationshipService) {
        this.residentService = residentService;
        this.familyRelationshipService = familyRelationshipService;
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

    @PostMapping("/{serialNumber}/relationship")
    public ResponseEntity<String> registerRelationship(@PathVariable("serialNumber") int serialNum,
                                                       @Valid @RequestBody RelationshipRegisterRequest request){
        familyRelationshipService.registerRelationship(serialNum, request);

        return ResponseEntity.status(HttpStatus.CREATED).body("Registerd");
    }

    @PutMapping("/{serialNumber}/relationship/{familySerialNumber}")
    public ResponseEntity<String> modifyRelationship(@PathVariable("serialNumber") int serialNumber,
                                                     @PathVariable("familySerialNumber") int familySerialNumber,
                                                     @Valid @RequestBody RelationshipModifyRequest request){
        familyRelationshipService.modifyRelationship(serialNumber, familySerialNumber, request);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Modified");
    }

    @DeleteMapping("/{serialNumber}/relationship/{familySerialNumber}")
    ResponseEntity<String> deleteRelationship(@PathVariable("serialNumber") int serialNumber,
                                              @PathVariable("familySerialNumber") int familySerialNumber){
        familyRelationshipService.deleteRelationship(serialNumber, familySerialNumber);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Deleted");
    }
}

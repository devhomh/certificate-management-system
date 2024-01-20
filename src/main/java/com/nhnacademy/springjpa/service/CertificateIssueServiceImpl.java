package com.nhnacademy.springjpa.service;

import com.nhnacademy.springjpa.entity.CertificateIssue;
import com.nhnacademy.springjpa.entity.Resident;
import com.nhnacademy.springjpa.repository.CertificateIssueRepository;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Random;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service("certificateIssueService")
public class CertificateIssueServiceImpl implements CertificateIssueService{
    private final ResidentService residentService;
    private final CertificateIssueRepository certificateIssueRepository;

    public CertificateIssueServiceImpl(ResidentService residentService,
                                       CertificateIssueRepository certificateIssueRepository) {
        this.residentService = residentService;
        this.certificateIssueRepository = certificateIssueRepository;
    }

    @Transactional
    @Override
    public CertificateIssue getCertificate(int serialNumber, String type){
        return certificateIssueRepository.
                        findByResident_residentSerialNumberAndCertificateTypeCode(serialNumber, type);
    }

    private long randomNumber(){
        Random random = new Random();
        return Math.abs(random.nextLong() % 9_000_000_000_000_000L) + 1_000_000_000_000_000L;
    }

    @Transactional
    @Override
    public CertificateIssue createCertificate(int serialNumber, String type){
       Resident resident = residentService.getResident(serialNumber);
       CertificateIssue certificate = new CertificateIssue();
       certificate.setCertificateConfirmationNumber(randomNumber());
       certificate.setResident(resident);
       certificate.setCertificateTypeCode(type);
       certificate.setCertificateIssueDate(LocalDate.now());

       certificateIssueRepository.save(certificate);

       return certificate;
    }
}

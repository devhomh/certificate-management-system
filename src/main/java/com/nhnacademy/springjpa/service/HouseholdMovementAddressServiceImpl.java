package com.nhnacademy.springjpa.service;

import com.nhnacademy.springjpa.domain.HouseholdAddressDto;
import com.nhnacademy.springjpa.repository.HouseholdCompositionResidentRepository;
import com.nhnacademy.springjpa.repository.HouseholdMovementAddressRepository;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service("householdMovementService")
public class HouseholdMovementAddressServiceImpl
        implements HouseholdMovementAddressService{
    private final HouseholdCompositionResidentRepository householdCompositionResidentRepository;
    private final HouseholdMovementAddressRepository householdMovementAddressRepository;

    public HouseholdMovementAddressServiceImpl(
            HouseholdCompositionResidentRepository householdCompositionResidentRepository,
            HouseholdMovementAddressRepository householdMovementAddressRepository) {
        this.householdCompositionResidentRepository = householdCompositionResidentRepository;
        this.householdMovementAddressRepository = householdMovementAddressRepository;
    }

    @Transactional
    @Override
    public List<HouseholdAddressDto> getAllAddressInfo(int serialNum) {
        int householdSerialNum = householdCompositionResidentRepository.getHouseholdSerialNum(serialNum);
        return householdMovementAddressRepository
                .getAllByPk_HouseholdSerialNumber(householdSerialNum)
                .stream()
                .sorted(Comparator.comparing(dto -> dto.getPk().getHouseMovementReportDate(), Comparator.reverseOrder()))
                .collect(Collectors.toList());
    }
}

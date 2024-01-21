package com.nhnacademy.springjpa.domain;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Value;
import org.springframework.lang.Nullable;

@Value
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
public class ResidentRegisterRequest {
    int residentSerialNumber;

    String name;

    String residentRegistrationNumber;

    String genderCode;

    LocalDateTime birthDate;

    String birthPlaceCode;

    String registrationBaseAddress;

    @Nullable
    LocalDateTime deathDate;

    @Nullable
    String deathPlaceCode;

    @Nullable
    String deathPlaceAddress;
}

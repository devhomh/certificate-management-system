package com.nhnacademy.springjpa.domain;

import javax.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
public class RelationshipRegisterRequest {
    int familySerialNumber;

    @Pattern(regexp = "^(father|mother|spouse|child)$", message = "Invalid familyRelationshipCode")
    String relationship;
}

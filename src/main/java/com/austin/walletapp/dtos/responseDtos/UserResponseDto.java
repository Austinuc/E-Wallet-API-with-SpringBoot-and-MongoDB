package com.austin.walletapp.dtos.responseDtos;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {

    private String uuid;

    private String firstName;

    private String lastName;

    private String walletUUId;

    private Date lastLoginDate;

    private String email;

    private String country;

    private String state;

    private String homeAddress;

    private String phoneNumber;
    private String token;
}

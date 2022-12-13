package com.austin.walletapp.dtos.responseDtos;

import javax.persistence.Column;
import java.util.UUID;

public class UserResponseDto {

    private UUID userUuid;

    private String firstName;

    private String lastName;

    private String userName;

    private String email;

    private String country;

    private String state;

    private String homeAddress;

    private String phoneNumber;
}

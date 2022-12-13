package com.austin.walletapp.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User extends Base {

    @Column(name = "user_uuid", columnDefinition = "VARCHAR(36)")
    @Type(type = "uuid-char")
    private UUID userUuid;

    @Column(nullable = false, length = 50)
    private String firstName;

    @Column(nullable = false, length = 50)
    private String lastName;

    @Column(nullable = false, length = 25, unique = true)
    private String userName;

    @Column(nullable = false)
    private String roles;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 50, unique = true)
    private String email;

    @Column(name = "country")
    private String country;

    @Column(name = "state")
    private String state;

    @Column(name = "home_address")
    private String homeAddress;

    @Column(length = 15)
    private String phoneNumber;

}

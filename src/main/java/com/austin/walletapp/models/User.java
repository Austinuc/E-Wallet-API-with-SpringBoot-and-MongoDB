package com.austin.walletapp.models;

import com.austin.walletapp.enums.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
//import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;


@Document("users")
@Data
@AllArgsConstructor
public class User extends Base implements Serializable {

    private static final long serialVersionUID = 2L;

    @Id
    private String id;

    @Indexed(unique = true)
    private String uuid;

    @Column(nullable = false, length = 50)
    private String firstName;

    @Column(nullable = false, length = 50)
    private String lastName;

    @Column(nullable = false)
    private String roles;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 20)
    private String walletUUId;

    private Date lastLoginDate;

    @Column(nullable = false)
    private String status;

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

    User(){
        super();
    }

}

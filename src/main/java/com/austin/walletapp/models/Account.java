package com.austin.walletapp.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Column;
import java.io.Serializable;


@Document("account")
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Account extends Base implements Serializable {

    private static final long serialVersionUID = 2L;

    @Indexed(unique = true)
    private String accountUUID;

    @Indexed(unique = true)
    private String userUUID;

    @Column(name = "account_name", nullable = false)
    private String account_name;

    @Column(name = "account_number", nullable = false)
    private String account_number;

    @Column(name = "bank_name", nullable = false)
    private String bank_name;

}

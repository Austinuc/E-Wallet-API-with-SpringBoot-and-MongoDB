package com.austin.walletapp.models;

import com.austin.walletapp.enums.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Document("wallet")
@Getter
@Setter
@Builder
//@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class Wallet implements Serializable {
    private static final long serialVersionUID = 2L;

    @Id
    private String id;
    @Indexed(unique = true)
    private String walletUUID;

    @Indexed(unique = true)
    private String userUUID;

    @Column(nullable = false)
    private BigDecimal balance;

    private Status status;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}

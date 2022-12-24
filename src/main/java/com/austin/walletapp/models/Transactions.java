package com.austin.walletapp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
import java.util.UUID;

@Document("transaction")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Transactions implements Serializable {

    private static final long serialVersionUID = 2L;

    @Id
    private String id;
    @Indexed(unique = true)
    private String uuid;

    @Column(nullable = false)
    private String transactionType;

    @Column(nullable = false)
    private String transactionStatus;

    private String transactionMessage;

    @Column(nullable = false)
    private BigDecimal transactionAmount;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}

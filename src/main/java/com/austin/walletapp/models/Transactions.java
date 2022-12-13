package com.austin.walletapp.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_transactions")
public class Transactions extends Base {

    @Column(name = "transaction_uuid", columnDefinition = "VARCHAR(36)")
    @Type(type = "uuid-char")
    private UUID transactionUuid;

    @Column(nullable = false)
    private String transactionType;

    @Column(nullable = false)
    private String transactionStatus;

    private String transactionStatusMessage;

    @Column(nullable = false)
    private double transactionAmount;
}

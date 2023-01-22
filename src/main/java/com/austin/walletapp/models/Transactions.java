package com.austin.walletapp.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.math.BigDecimal;

@Document("transaction")
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
public class Transactions  extends Base implements Serializable {

    private static final long serialVersionUID = 2L;

    @Indexed(unique = true)
    private String reference;
    private String domain;
    private String status;
    private String source;
    private String transactionType;
    private String message;
    private String description;
    private String request_code;
    private String customerEmail;
    private boolean paid;
    private BigDecimal amount;
    private String gateway_response;
    private String channel;
    private String currency;
    private String ip_address;

//    =========== Withdrawal ================

    private String integration;
    private String reason;
    private String transfer_code;

    public Transactions() {
        super();
    }
}

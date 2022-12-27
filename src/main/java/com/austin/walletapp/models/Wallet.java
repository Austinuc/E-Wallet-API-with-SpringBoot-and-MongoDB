package com.austin.walletapp.models;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Column;
import java.io.Serializable;
import java.math.BigDecimal;

@Document("wallet")
@Getter
@Setter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
public class Wallet extends Base implements Serializable {
    private static final long serialVersionUID = 2L;
    @Indexed(unique = true)
    private String walletUUID;

    @Indexed(unique = true)
    private String email;

    @Column(nullable = false)
    private BigDecimal balance;

    public Wallet() {super();}

}

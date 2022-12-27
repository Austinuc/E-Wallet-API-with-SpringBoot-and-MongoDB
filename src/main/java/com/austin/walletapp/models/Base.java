package com.austin.walletapp.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
public abstract class Base {
    @Id
    private String id;
    @CreatedDate
    private Date createdDate;

    @LastModifiedDate
    private Date updatedDate;

    Base(){
        this.createdDate= new Date();
        this.updatedDate = new Date();
    }

    @PrePersist
    public void setCreatedAt() {
        createdDate = new Date();
    }
    @PreUpdate
    public void setUpdatedAt() {
        updatedDate = new Date();
    }
}

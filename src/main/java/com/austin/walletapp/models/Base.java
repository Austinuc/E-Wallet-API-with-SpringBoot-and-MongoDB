package com.austin.walletapp.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.Date;

@Data
@AllArgsConstructor
public class Base {
    @CreatedDate
    private Date createdDate;

    @LastModifiedDate
    private Date updatedDate;

    Base(){
        this.createdDate= new Date();
        this.updatedDate = new Date();
    }

    @PrePersist
    private void setCreatedAt() {
        createdDate = new Date();
    }
    @PreUpdate
    private void setUpdatedAt() {
        updatedDate = new Date();
    }
}

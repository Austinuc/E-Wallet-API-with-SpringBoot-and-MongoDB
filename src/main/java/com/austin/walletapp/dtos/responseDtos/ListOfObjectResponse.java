package com.austin.walletapp.dtos.responseDtos;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ListOfObjectResponse<T> {
    private List<T> objects;

    public ListOfObjectResponse () {
        objects = new ArrayList<>();
    }
}

package com.example.userservice.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsRequestModel {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Collection<AddressRequestModel> addresses = new ArrayList<>();
}

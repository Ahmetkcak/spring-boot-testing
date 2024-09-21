package com.odeon.odeonDemo.business.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class GetUserInfoResponse {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String city;
    private Set<String> authorities;
}

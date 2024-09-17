package com.odeon.odeonDemo.business.dtos.responses;

import com.odeon.odeonDemo.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class LoggedInResponse {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String accessToken;
    private String refreshToken;
}

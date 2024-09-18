package com.odeon.odeonDemo.business.dtos.responses;

import com.odeon.odeonDemo.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class LoggedInResponse {
    private String accessToken;
    private String refreshToken;
}

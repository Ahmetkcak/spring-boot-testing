package com.odeon.odeonDemo.business.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class RefreshedTokenResponse {
    private String accessToken;
    private String refreshToken;
}

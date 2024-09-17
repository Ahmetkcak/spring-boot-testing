package com.odeon.odeonDemo.business.abstracts;

import com.odeon.odeonDemo.business.dtos.requests.LoginRequest;
import com.odeon.odeonDemo.business.dtos.responses.LoggedInResponse;
import com.odeon.odeonDemo.business.dtos.responses.RefreshedTokenResponse;

public interface AuthService {
    LoggedInResponse login(LoginRequest loginRequest, String ipAddress) throws Exception;
    RefreshedTokenResponse refreshToken(String refreshToken, String ipAddress);
}

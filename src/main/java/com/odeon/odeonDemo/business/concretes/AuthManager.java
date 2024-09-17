package com.odeon.odeonDemo.business.concretes;

import com.odeon.odeonDemo.business.abstracts.AuthService;
import com.odeon.odeonDemo.business.abstracts.RefreshTokenService;
import com.odeon.odeonDemo.business.abstracts.UserService;
import com.odeon.odeonDemo.business.dtos.requests.LoginRequest;
import com.odeon.odeonDemo.business.dtos.responses.LoggedInResponse;
import com.odeon.odeonDemo.business.dtos.responses.RefreshedTokenResponse;
import com.odeon.odeonDemo.business.messages.AuthMessages;
import com.odeon.odeonDemo.core.services.JwtService;
import com.odeon.odeonDemo.entities.RefreshToken;
import com.odeon.odeonDemo.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class AuthManager implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;
    private final RefreshTokenService refreshTokenService;

    @Override
    public LoggedInResponse login(LoginRequest loginRequest, String ipAddress) throws Exception {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        if (!authentication.isAuthenticated()) {
            throw new Exception(AuthMessages.LOGIN_FAILED);
        }
        User user = userService.findByUserName(loginRequest.getEmail());
        refreshTokenService.revokeOldTokens(user, ipAddress);
        RefreshToken refreshToken = refreshTokenService.create(user);
        String accessToken = generateJwt(user);
        return new LoggedInResponse(accessToken, refreshToken.getToken());
    }

    @Override
    public RefreshedTokenResponse refreshToken(String refreshToken, String ipAddress) {
        RefreshToken token = refreshTokenService.verify(refreshToken);
        RefreshToken newToken = refreshTokenService.rotate(token, ipAddress);
        refreshTokenService.revokeOldTokens(token.getUser(), ipAddress);
        String accessToken = generateJwt(token.getUser());
        return new RefreshedTokenResponse(accessToken, newToken.getToken());
    }

    private String generateJwt(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId());
        claims.put("username", user.getEmail());
        return jwtService.generateToken(user.getEmail(), claims);
    }
}

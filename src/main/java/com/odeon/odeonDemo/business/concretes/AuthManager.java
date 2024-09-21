package com.odeon.odeonDemo.business.concretes;

import com.odeon.odeonDemo.business.abstracts.AuthService;
import com.odeon.odeonDemo.business.abstracts.RefreshTokenService;
import com.odeon.odeonDemo.business.abstracts.UserService;
import com.odeon.odeonDemo.business.dtos.requests.LoginRequest;
import com.odeon.odeonDemo.business.dtos.responses.LoggedInResponse;
import com.odeon.odeonDemo.business.dtos.responses.RefreshedTokenResponse;
import com.odeon.odeonDemo.business.messages.AuthMessages;
import com.odeon.odeonDemo.core.services.JwtService;
import com.odeon.odeonDemo.core.utilities.exceptions.types.BusinessException;
import com.odeon.odeonDemo.core.utilities.mapping.ModelMapperService;
import com.odeon.odeonDemo.entities.RefreshToken;
import com.odeon.odeonDemo.entities.User;
import com.odeon.odeonDemo.entities.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AuthManager implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;
    private final RefreshTokenService refreshTokenService;
    private final ModelMapperService modelMapperService;

    @Override
    public LoggedInResponse login(LoginRequest loginRequest, String ipAddress) throws Exception {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        if (!authentication.isAuthenticated()) {
            throw new BusinessException(AuthMessages.LOGIN_FAILED);
        }
        User user = userService.findByUserName(loginRequest.getEmail());
        refreshTokenService.revokeOldTokens(user, ipAddress);
        RefreshToken refreshToken = refreshTokenService.create(user);
        String accessToken = generateJwt(user);

        Set<String> roles = user.getAuthorities().stream().map(Role::getName).collect(Collectors.toSet());
        return new LoggedInResponse(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getCity(), roles,accessToken, refreshToken.getToken());
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

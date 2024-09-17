package com.odeon.odeonDemo.api.controllers;

import com.odeon.odeonDemo.business.abstracts.AuthService;
import com.odeon.odeonDemo.business.dtos.requests.LoginRequest;
import com.odeon.odeonDemo.business.dtos.responses.LoggedInResponse;
import com.odeon.odeonDemo.business.dtos.responses.RefreshedTokenResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Value("${jwt.refresh.key}")
    private String refreshTokenKey;
    @Value("${jwt.refresh.days}")
    private int refreshTokenExpiryDays;

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest, HttpServletResponse response, HttpServletRequest request) throws Exception {
        LoggedInResponse loggedInResponse = authService.login(loginRequest, getIpAddress(request));
        setCookie(refreshTokenKey, loggedInResponse.getRefreshToken(), refreshTokenExpiryDays * 24 * 60 * 60, response);
        return loggedInResponse.getAccessToken();
    }

    @PostMapping("/refresh")
    public String refreshToken(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = getCookie(request, refreshTokenKey);
        RefreshedTokenResponse refreshedTokenResponse = authService.refreshToken(refreshToken, getIpAddress(request));
        setCookie(refreshTokenKey, refreshedTokenResponse.getRefreshToken(), refreshTokenExpiryDays * 24 * 60 * 60, response);
        return refreshedTokenResponse.getAccessToken();
    }

    @GetMapping
    public String hello() {
        return "Hello World!";
    }

    // BaseController tanımlanıp oradan alınabilir
    private String getIpAddress(HttpServletRequest request) {
        String ipAddress = request.getRemoteAddr();
        if (ipAddress == null) {
            ipAddress = request.getHeader("X-Forwarded-For");
        }
        return ipAddress;
    }

    private String getCookie(HttpServletRequest request, String key) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(key)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    private void setCookie(String key, String value, int expiry, HttpServletResponse response) {
        Cookie cookie = new Cookie(key, value);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(expiry);
        cookie.setSecure(true);
        response.addCookie(cookie);
    }
}

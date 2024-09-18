package com.odeon.odeonDemo.business.security;

import com.odeon.odeonDemo.business.constants.Roles;
import com.odeon.odeonDemo.core.services.SecurityService;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.stereotype.Service;


@Service
public class SecurityConfigServiceImpl implements SecurityService {

    private static final String[] AUTH_WHITELIST = {
            "/api/users",
            "/api/auth/**"
    };

    @Override
    public HttpSecurity configureSecurity(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(x -> x
                .requestMatchers(AUTH_WHITELIST).permitAll()
                .requestMatchers(HttpMethod.POST, "/api/flights/create").hasAnyAuthority(Roles.ADMIN)
                .requestMatchers(HttpMethod.POST, "/api/flights/delete/").hasAnyAuthority(Roles.ADMIN)
                .requestMatchers(HttpMethod.GET, "/api/flights/getAll").hasAnyAuthority(Roles.ADMIN, Roles.USER)
                .anyRequest().authenticated());
        return http;
    }
}
package com.odeon.odeonDemo.business.security;

import com.odeon.odeonDemo.business.constants.Roles;
import com.odeon.odeonDemo.core.services.SecurityService;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Service;

//@Configuration
//@EnableWebSecurity
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
                .requestMatchers(HttpMethod.GET).permitAll()
                .requestMatchers(HttpMethod.POST).permitAll()
                .requestMatchers(HttpMethod.POST, "/api/users/").hasAnyAuthority(Roles.ADMIN)
                .anyRequest().authenticated());
        return http;
    }
}
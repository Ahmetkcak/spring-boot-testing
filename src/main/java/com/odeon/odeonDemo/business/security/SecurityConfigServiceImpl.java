package com.odeon.odeonDemo.business.security;

import com.odeon.odeonDemo.business.constants.Roles;
import com.odeon.odeonDemo.core.services.SecurityService;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.stereotype.Service;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Service
public class SecurityConfigServiceImpl implements SecurityService {

    private static final String[] AUTH_WHITELIST = {
            "/api/users/**",
            "/api/auth/**",
            "/api/flights/**",
    };

    @Override
    public HttpSecurity configureSecurity(HttpSecurity http) throws Exception {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOriginPattern("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        CorsFilter corsFilter = new CorsFilter(source);

        http.cors(csrf -> csrf.disable()).csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(x -> x
                        .requestMatchers(HttpMethod.POST, "/api/users/getUserInfoWithRoles").permitAll()
                        .requestMatchers(AUTH_WHITELIST).permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/flights/create").hasAnyAuthority(Roles.ADMIN)
                        .requestMatchers(HttpMethod.POST, "/api/flights/delete/").hasAnyAuthority(Roles.ADMIN)
                        .requestMatchers(HttpMethod.GET, "/api/flights/getAll").hasAnyAuthority(Roles.ADMIN, Roles.USER)
                        .requestMatchers(HttpMethod.POST, "/api/reservations/create").hasAnyAuthority(Roles.ADMIN, Roles.USER)
                        .requestMatchers(HttpMethod.DELETE, "/api/reservations/delete/").hasAnyAuthority(Roles.ADMIN, Roles.USER)
                        .anyRequest().authenticated())
                .addFilterBefore(corsFilter, CorsFilter.class);

        return http;
    }
}
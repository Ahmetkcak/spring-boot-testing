package com.odeon.odeonDemo.business.abstracts;

import com.odeon.odeonDemo.business.dtos.requests.RegisterRequest;
import com.odeon.odeonDemo.entities.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    void register(RegisterRequest registerRequest);
    User findByUserName(String userName);
}

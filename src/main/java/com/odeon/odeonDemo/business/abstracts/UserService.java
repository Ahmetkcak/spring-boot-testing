package com.odeon.odeonDemo.business.abstracts;

import com.odeon.odeonDemo.business.dtos.requests.RegisterRequest;
import com.odeon.odeonDemo.entities.User;

public interface UserService {
    void register(RegisterRequest registerRequest);
    User findByUserName(String userName);
}

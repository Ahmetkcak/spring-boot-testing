package com.odeon.odeonDemo.api.controllers;

import com.odeon.odeonDemo.business.abstracts.UserService;
import com.odeon.odeonDemo.business.dtos.requests.RegisterRequest;
import com.odeon.odeonDemo.business.dtos.responses.GetUserInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UsersController {
    private final UserService userService;

    @PostMapping("/register")
    public void register(@RequestBody RegisterRequest registerRequest) {
        userService.register(registerRequest);
    }

    @PostMapping("/getUserInfoWithRoles")
    public GetUserInfoResponse getUserInfoWithRoles(@RequestBody String email) {
        return userService.getUserInfoWithRoles(email);
    }
}

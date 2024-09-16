package com.odeon.odeonDemo.api.controllers;

import com.odeon.odeonDemo.business.abstracts.UserService;
import com.odeon.odeonDemo.business.dtos.requests.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UsersController {
    private final UserService userService;

    @PostMapping
    public void register(@RequestBody RegisterRequest registerRequest) {
        userService.register(registerRequest);
    }

    // Test endpoint
    @GetMapping
    public String hello() {
        return "Hello World!";
    }
}

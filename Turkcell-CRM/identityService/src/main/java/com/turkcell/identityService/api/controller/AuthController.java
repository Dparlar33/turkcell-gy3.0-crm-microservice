package com.turkcell.identityService.api.controller;

import com.turkcell.identityService.business.abstracts.AuthService;
import com.turkcell.identityService.business.dtos.requests.LoginRequest;
import com.turkcell.identityService.business.dtos.requests.RegisterRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/identityservice/api/v1/identities")
@AllArgsConstructor
public class AuthController  {

    private final AuthService authService;

    @PostMapping("/register")
    public void register(@RequestBody RegisterRequest request) {
        authService.register(request);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }
}
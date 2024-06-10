package com.turkcell.identityService.business.concretes;

import com.turkcell.crm.core.util.JwtService;
import com.turkcell.identityService.business.abstracts.AuthService;
import com.turkcell.identityService.business.abstracts.UserService;
import com.turkcell.identityService.business.dtos.requests.LoginRequest;
import com.turkcell.identityService.business.dtos.requests.RegisterRequest;
import com.turkcell.identityService.business.rules.AuthBusinessRules;
import com.turkcell.identityService.entitites.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthManager implements AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final JwtService jwtService;
    private final AuthBusinessRules authBusinessRules;
    @Override
    public String login(LoginRequest loginRequest) {

        this.authBusinessRules.checkIfAuthenticated(loginRequest);

        UserDetails user = userService.loadUserByUsername(loginRequest.getEmail());
        return generateJwtToken(user);
    }
    @Override
    public void register(RegisterRequest request) {

        User user = createUserFromRequest(request);

        userService.add(user);
    }
    private String generateJwtToken(UserDetails user) {

        Map<String, Object> claims = new HashMap<>();

        List<String> roles = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        claims.put("roles", roles);

        return jwtService.generateToken(user.getUsername(), roles);
    }
    private User createUserFromRequest(RegisterRequest request) {

        User user = new User();
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        return user;
    }
}

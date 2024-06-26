package com.turkcell.identityService.business.abstracts;

import com.turkcell.identityService.business.dtos.requests.RegisterRequest;
import com.turkcell.identityService.entitites.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    void add(User user);
}

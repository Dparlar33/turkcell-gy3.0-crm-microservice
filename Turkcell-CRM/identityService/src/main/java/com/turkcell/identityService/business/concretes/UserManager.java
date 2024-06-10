package com.turkcell.identityService.business.concretes;

import com.turkcell.identityService.business.abstracts.UserService;
import com.turkcell.identityService.business.dtos.requests.RegisterRequest;
import com.turkcell.identityService.business.messages.AuthMessages;
import com.turkcell.identityService.core.utilities.exceptions.types.BusinessException;
import com.turkcell.identityService.core.utilities.mapping.ModelMapperService;
import com.turkcell.identityService.dataAccess.UserRepository;
import com.turkcell.identityService.entitites.User;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserManager implements UserService {

    private final UserRepository userRepository;

    @Override
    public void add(User user) {
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return userRepository.findUserByEmail(username).orElseThrow(() -> new BadCredentialsException(""));
    }
}
package com.turkcell.identityService.business.rules;

import com.turkcell.identityService.business.dtos.requests.LoginRequest;
import com.turkcell.identityService.business.messages.AuthMessages;
import com.turkcell.identityService.core.business.abstracts.MessageService;
import com.turkcell.identityService.core.utilities.exceptions.types.BusinessException;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class AuthBusinessRules {

    private MessageService messageService;
    private AuthenticationManager authenticationManager;

    public void checkIfAuthenticated(LoginRequest loginRequest) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        if (!authentication.isAuthenticated())
            throw new BusinessException(messageService.getMessage(AuthMessages.LOGIN_FAILED));
    }
}

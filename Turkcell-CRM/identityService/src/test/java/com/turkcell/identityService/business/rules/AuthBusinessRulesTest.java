package com.turkcell.identityService.business.rules;

import com.turkcell.identityService.business.dtos.requests.LoginRequest;
import com.turkcell.identityService.business.messages.AuthMessages;
import com.turkcell.identityService.core.business.abstracts.MessageService;
import com.turkcell.identityService.core.utilities.exceptions.types.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AuthBusinessRulesTest {
    @Mock
    private MessageService messageService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthBusinessRules authBusinessRules;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCheckIfAuthenticatedSuccess() {

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("test@example.com");
        loginRequest.setPassword("password");

        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);

        authBusinessRules.checkIfAuthenticated(loginRequest);

        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    void testCheckIfAuthenticatedFailed() {

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("test@example.com");
        loginRequest.setPassword("password");

        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(false);
        when(messageService.getMessage(AuthMessages.LOGIN_FAILED)).thenReturn("Login failed");

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            authBusinessRules.checkIfAuthenticated(loginRequest);
        });

        assertEquals("Login failed", exception.getMessage());
    }
}
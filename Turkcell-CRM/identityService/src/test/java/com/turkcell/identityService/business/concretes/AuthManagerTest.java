package com.turkcell.identityService.business.concretes;

import com.turkcell.crm.core.util.JwtService;
import com.turkcell.identityService.business.abstracts.UserService;
import com.turkcell.identityService.business.dtos.requests.LoginRequest;
import com.turkcell.identityService.business.dtos.requests.RegisterRequest;
import com.turkcell.identityService.business.messages.AuthMessages;
import com.turkcell.identityService.business.rules.AuthBusinessRules;
import com.turkcell.identityService.entitites.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class AuthManagerTest {
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UserService userService;
    @Mock
    private JwtService jwtService;
    @Mock
    private AuthBusinessRules authBusinessRules;
    @InjectMocks
    private AuthManager authManager;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void login_whenAuthenticated_shouldReturnJwtToken() {

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("user@gmail.com");
        loginRequest.setPassword("password");

        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("user@gmail.com");
        when(userDetails.getAuthorities()).thenReturn(new ArrayList<>());

        when(userService.loadUserByUsername(loginRequest.getEmail())).thenReturn(userDetails);
        when(jwtService.generateToken(anyString(), anyList())).thenReturn("mocked jwt token");

        String token = authManager.login(loginRequest);

        verify(authBusinessRules, times(1)).checkIfAuthenticated(loginRequest);
        verify(userService, times(1)).loadUserByUsername(loginRequest.getEmail());
        verify(jwtService, times(1)).generateToken(userDetails.getUsername(), new ArrayList<>());

        assertEquals("mocked jwt token", token);
    }

    @Test
    void login_whenNotAuthenticated_shouldThrowException() {

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("user@gmail.com");
        loginRequest.setPassword("password");

        doThrow(new RuntimeException(AuthMessages.LOGIN_FAILED)).when(authBusinessRules).checkIfAuthenticated(loginRequest);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> authManager.login(loginRequest));
        assertEquals(AuthMessages.LOGIN_FAILED, exception.getMessage());
    }

    @Test
    void register_shouldAddUser_whenValidRequest() {

        RegisterRequest request = new RegisterRequest();
        request.setEmail("user@gmail.com");
        request.setFirstName("First");
        request.setLastName("Last");
        request.setPassword("password");

        User user = new User();
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPassword("encoded password");

        when(passwordEncoder.encode(request.getPassword())).thenReturn("encoded password");

        authManager.register(request);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userService, times(1)).add(userCaptor.capture());

        User capturedUser = userCaptor.getValue();
        assertEquals(request.getEmail(), capturedUser.getEmail());
        assertEquals(request.getFirstName(), capturedUser.getFirstName());
        assertEquals(request.getLastName(), capturedUser.getLastName());
        assertEquals("encoded password", capturedUser.getPassword());
    }
}
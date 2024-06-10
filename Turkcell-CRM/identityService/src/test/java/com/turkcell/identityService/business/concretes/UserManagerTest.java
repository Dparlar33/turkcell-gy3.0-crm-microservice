package com.turkcell.identityService.business.concretes;

import com.turkcell.identityService.dataAccess.UserRepository;
import com.turkcell.identityService.entitites.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserManagerTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserManager userManager;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void add_whenValidUser_shouldSaveUser() {

        User user = new User();
        user.setEmail("user@gmail.com");

        userManager.add(user);

        verify(userRepository, times(1)).save(user);
    }

    @Test
    void loadUserByUsername_whenUserExists_shouldReturnUserDetails() {

        User user = new User();
        user.setEmail("user@gmail.com");
        user.setPassword("password");

        when(userRepository.findUserByEmail("user@gmail.com")).thenReturn(Optional.of(user));

        UserDetails userDetails = userManager.loadUserByUsername("user@gmail.com");

        assertEquals("user@gmail.com", userDetails.getUsername());
        assertEquals("password", userDetails.getPassword());
    }

    @Test
    void loadUserByUsername_whenUserDoesNotExist_shouldThrowBadCredentialsException() {

        when(userRepository.findUserByEmail("user@gmail.com")).thenReturn(Optional.empty());

        assertThrows(BadCredentialsException.class, () -> userManager.loadUserByUsername("user@gmail.com"));
    }
}
package com.incubyte.sweetshop.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private BCryptPasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        passwordEncoder = new BCryptPasswordEncoder();
        userService = new UserService(userRepository, passwordEncoder);
    }

    @Test
    void shouldRegisterNewUserSuccessfully() {
        User user = new User();
        user.setEmail("test@incubyte.com");
        user.setPassword("password123");

        when(userRepository.existsByEmail("test@incubyte.com"))
                .thenReturn(false);

        when(userRepository.save(any(User.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        User savedUser = userService.register(user);

        assertNotNull(savedUser);
        assertEquals("test@incubyte.com", savedUser.getEmail());
        assertNotEquals("password123", savedUser.getPassword());
        assertEquals("USER", savedUser.getRole());
    }
}

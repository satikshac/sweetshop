package com.incubyte.sweetshop.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.incubyte.sweetshop.model.User;
import com.incubyte.sweetshop.repository.UserRepository;

class UserServiceTest {

    @Test
    void shouldRegisterUser() {

        // ✅ Fake repository (NO Mockito, NO lambda)
        UserRepository fakeRepository = new UserRepository() {
            @Override
            public User save(User user) {
                return user;
            }
        };

        UserService userService =
                new UserService(fakeRepository, new BCryptPasswordEncoder());

        User user = new User();

        // ⚠️ USE ONLY FIELDS THAT EXIST IN YOUR User ENTITY
        user.setPassword("password123");

        User savedUser = userService.register(user);

        assertNotNull(savedUser);
        assertNotEquals("password123", savedUser.getPassword());
        assertEquals("USER", savedUser.getRole());
    }
}

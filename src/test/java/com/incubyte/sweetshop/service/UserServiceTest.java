package com.incubyte.sweetshop.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.incubyte.sweetshop.model.User;
import com.incubyte.sweetshop.repository.UserRepository;

class UserServiceTest {

    @Test
    void shouldRegisterUser() {

        UserRepository repo = new UserRepository() {
            @Override
            public <S extends User> S save(S entity) {
                return entity;
            }
        };

        UserService service =
                new UserService(repo, new BCryptPasswordEncoder());

        User user = new User();
        user.setPassword("password123");

        User savedUser = service.register(user);

        assertNotNull(savedUser);
        assertNotEquals("password123", savedUser.getPassword());
        assertEquals("USER", savedUser.getRole());
    }

    @Test
    void shouldNotRegisterUserWhenPasswordIsEmpty() {

        UserRepository repo = new UserRepository() {
            @Override
            public <S extends User> S save(S entity) {
                return entity;
            }
        };

        UserService service =
                new UserService(repo, new BCryptPasswordEncoder());

        User user = new User();
        user.setPassword("");

        Exception ex = assertThrows(
                IllegalArgumentException.class,
                () -> service.register(user)
        );

        assertEquals("Password cannot be empty", ex.getMessage());
    }
}

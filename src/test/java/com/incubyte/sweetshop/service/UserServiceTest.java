package com.incubyte.sweetshop.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.incubyte.sweetshop.model.User;
import com.incubyte.sweetshop.repository.UserRepository;

class UserServiceTest {

    private UserRepository fakeRepository() {
        return new UserRepository() {

            @Override
            public <S extends User> S save(S entity) {
                return entity;
            }

            @Override
            public Optional<User> findByUsername(String username) {
                return Optional.empty();
            }
        };
    }

    @Test
    void shouldRegisterUser() {
        UserService service =
                new UserService(fakeRepository(), new BCryptPasswordEncoder());

        User user = new User();
        user.setPassword("password123");

        User savedUser = service.register(user);

        assertNotNull(savedUser);
        assertNotEquals("password123", savedUser.getPassword());
        assertEquals("USER", savedUser.getRole());
    }

    @Test
    void shouldNotRegisterUserWhenPasswordIsEmpty() {
        UserService service =
                new UserService(fakeRepository(), new BCryptPasswordEncoder());

        User user = new User();
        user.setPassword("");

        Exception ex = assertThrows(
                IllegalArgumentException.class,
                () -> service.register(user)
        );

        assertEquals("Password cannot be empty", ex.getMessage());
    }
}

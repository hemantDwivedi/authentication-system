package com.assignment.authenticationsystem.repository;

import com.assignment.authenticationsystem.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void findsUserThatExistsByUsernameOrEmail() {
        User user = getUser();
        userRepository.save(user);
        User expected = userRepository.findByUsernameOrEmail("admin", "admin").orElse(null);
        assertNotNull(expected);
        assertEquals(user.getUsername(), expected.getUsername());
    }
    @Test
    void findsUserThatDoesNotExistsByUsernameOrEmail() {
        String username = "admin";
        String email = "admin@gmail.com";
        User expected = userRepository.findByUsernameOrEmail(username, email).orElse(null);
        assertNull(expected);
    }

    @Test
    void checksWhenUserExistsByUsernameOrEmail() {
        User user = getUser();
        userRepository.save(user);
        Boolean expected = userRepository.existsByUsernameOrEmail("admin", "admin");
        assertNotNull(expected);
    }
    @Test
    void checksWhenUserDoesNotExistsByUsernameOrEmail() {
        String username = "admin";
        String email= "admin@gmail.com";
        Boolean expected = userRepository.existsByUsernameOrEmail(username, email);
        assertEquals(expected, false);
    }

    private User getUser(){
        return new User(
                1L,
                "admin",
                "admin",
                "admin@gmail.com",
                null,
                null);
    }
}
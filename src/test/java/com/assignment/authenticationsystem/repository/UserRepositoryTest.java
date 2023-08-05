package com.assignment.authenticationsystem.repository;

import com.assignment.authenticationsystem.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    void findByUsernameOrEmailTest() {
        User user = getUser();
        userRepository.save(user);
        User dbUser = userRepository.findByUsernameOrEmail("admin", "admin").orElse(null);
        assertNotNull(dbUser);
        assertEquals(user.getUsername(), dbUser.getUsername());
    }

    @Test
    void existsByUsernameOrEmailTest() {
        User user = getUser();
        userRepository.save(user);
        Boolean isUserExists = userRepository.existsByUsernameOrEmail("admin", "admin");
        assertNotNull(isUserExists);
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
package com.assignment.authenticationsystem.token;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class TokenRepositoryTest {

    @Autowired private TokenRepository tokenRepository;

    @AfterEach
    void tearDown() {
        tokenRepository.deleteAll();
    }

    @Test
    void checksWhenTokenExistsByName() {
        var token = getToken();
        tokenRepository.save(token);
        var expected = tokenRepository.findByToken(token.getToken()).orElse(null);
        assertNotNull(expected);
        assertEquals(token.getToken(), expected.getToken());
    }

    @Test
    void checksWhenTokenDoesNotExistsByName() {
        var token = getToken();
        var expected = tokenRepository.findByToken(token.getToken()).orElse(null);
        assertNull(expected);
    }

    private static Token getToken() {
        return new Token(1L, "mock-test-token", TokenType.BEARER, null);
    }
}
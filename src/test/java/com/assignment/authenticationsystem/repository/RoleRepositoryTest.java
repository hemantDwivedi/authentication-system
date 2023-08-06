package com.assignment.authenticationsystem.repository;

import com.assignment.authenticationsystem.model.Role;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    @AfterEach
    void tearDown() {
        roleRepository.deleteAll();
    }

    @Test
    void findsRoleThatExitsByName() {
        Role role = getRole();
        roleRepository.save(role);
        var expected = roleRepository.findByName("ROLE_ADMIN").orElse(null);
        assertNotNull(expected);
        assertEquals(role.getName(), expected.getName());
    }

    @Test
    void findsRoleThatNotExitsByName() {
        var expected = roleRepository.findByName("ROLE_ADMIN").orElse(null);
        assertNull(expected);
    }

    private Role getRole(){
        return new Role(1L, "ROLE_ADMIN");
    }
}
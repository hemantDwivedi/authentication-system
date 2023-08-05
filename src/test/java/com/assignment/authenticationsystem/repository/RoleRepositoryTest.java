package com.assignment.authenticationsystem.repository;

import com.assignment.authenticationsystem.model.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    @BeforeEach
    void setUp() {
        roleRepository.deleteAll();
    }

    @Test
    void findByName() {
        Role role = getRole();
        roleRepository.save(role);
        var foundRole = roleRepository.findByName("ROLE_ADMIN").orElse(null);
        assertNotNull(foundRole);
        assertEquals(role.getName(), foundRole.getName());
    }

    private Role getRole(){
        return new Role(1L, "ROLE_ADMIN");
    }
}
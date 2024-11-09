package com.odeon.odeonDemo.dataAccess;

import com.odeon.odeonDemo.dataAccess.abstracts.RoleRepository;
import com.odeon.odeonDemo.entities.Role;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RoleRepositoryTests {

    @Autowired
    private RoleRepository roleRepository;


    @Test
    public void RoleRepository_SaveAll_ReturnSavedRoles() {
        // Arrange
        Role role = Role.builder()
                .name("ADMIN")
                .build();

        // Act
        Role savedRole = roleRepository.save(role);

        // Assert
        Assertions.assertThat(savedRole).isNotNull();
        Assertions.assertThat(savedRole.getId()).isGreaterThan(0);
    }
}

package com.odeon.odeonDemo.dataAccess.dataSeeders;

import com.odeon.odeonDemo.dataAccess.abstracts.RoleRepository;
import com.odeon.odeonDemo.dataAccess.abstracts.UserRepository;
import com.odeon.odeonDemo.entities.Role;
import com.odeon.odeonDemo.entities.User;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Component
@AllArgsConstructor
public class DatabaseSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        if (userRepository.count() == 0) {
            Role adminRole = new Role();
            adminRole.setName("Admin");
            roleRepository.save(adminRole);

            Role userRole = new Role();
            userRole.setName("User");
            roleRepository.save(userRole);

            String encodedPasswordAdmin = passwordEncoder.encode("12345");
            User adminUser = new User(
                    "admin",
                    "admin",
                    "admin@mail.com",
                    encodedPasswordAdmin,
                    LocalDate.of(1990, 1, 1),
                    "istanbul"
            );

            Set<Role> adminRoles = new HashSet<>();
            adminRoles.add(adminRole);
            adminUser.setAuthorities(adminRoles);
            userRepository.save(adminUser);

            String encodedPasswordUser = passwordEncoder.encode("12345");
            User regularUser = new User(
                    "user",
                    "user",
                    "user@mail.com",
                    encodedPasswordUser,
                    LocalDate.of(2002, 1, 9),
                    "antalya"
            );

            Set<Role> userRoles = new HashSet<>();
            userRoles.add(userRole);
            regularUser.setAuthorities(userRoles);
            userRepository.save(regularUser);
        }
    }
}

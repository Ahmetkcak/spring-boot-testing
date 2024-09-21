package com.odeon.odeonDemo.dataAccess.abstracts;

import com.odeon.odeonDemo.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}

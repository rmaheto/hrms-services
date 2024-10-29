package org.muhikira.authservice.repository;

import java.util.List;
import java.util.Optional;
import org.muhikira.authservice.entity.User;
import org.muhikira.authservice.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByUsername(String username);
  List<User> findByRoles_Name(RoleName roleName);
  boolean existsByUsername(String username);
}

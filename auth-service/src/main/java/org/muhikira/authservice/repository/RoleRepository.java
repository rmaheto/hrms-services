package org.muhikira.authservice.repository;

import java.util.Optional;
import org.muhikira.authservice.entity.Role;
import org.muhikira.authservice.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findByName(RoleName roleName);
  boolean existsByName(RoleName roleName);
}

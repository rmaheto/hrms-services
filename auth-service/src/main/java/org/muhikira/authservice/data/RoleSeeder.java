package org.muhikira.authservice.data;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.muhikira.authservice.entity.Role;
import org.muhikira.authservice.model.RoleName;
import org.muhikira.authservice.repository.RoleRepository;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Order(1)
public class RoleSeeder {

  private final RoleRepository roleRepository;

  @PostConstruct
  public void seedRoles() {
    for (RoleName roleName : RoleName.values()) {
      if (!roleRepository.existsByName(roleName)) {
        Role role = new Role();
        role.setName(roleName);
        roleRepository.save(role);
      }
    }
  }
}

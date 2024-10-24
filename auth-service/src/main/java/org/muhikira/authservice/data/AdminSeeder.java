package org.muhikira.authservice.data;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.muhikira.authservice.entity.Role;
import org.muhikira.authservice.entity.User;
import org.muhikira.authservice.exception.RoleNotFoundException;
import org.muhikira.authservice.model.RoleName;
import org.muhikira.authservice.repository.RoleRepository;
import org.muhikira.authservice.repository.UserRepository;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
@Order(2)
@Slf4j
public class AdminSeeder {

  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final PasswordEncoder passwordEncoder;

  @PostConstruct
  public void seedAdminUser() {
    // Ensure roles are seeded first
    Optional<Role> adminRoleOpt = roleRepository.findByName(RoleName.ROLE_ADMIN);
    if (adminRoleOpt.isEmpty()) {
      throw new RoleNotFoundException("ROLE_ADMIN must exist before seeding admin user.");
    }

    // Check if the admin user already exists
    String adminUsername = "admin";
    if (!userRepository.existsByUsername(adminUsername)) {
      User adminUser = new User();
      adminUser.setUsername(adminUsername);
      adminUser.setPassword(passwordEncoder.encode("admin"));

      Set<Role> roles = new HashSet<>();
      roles.add(adminRoleOpt.get());
      adminUser.setRoles(roles);

      userRepository.save(adminUser);
      log.info("Admin user created");
    } else {
      log.warn("Admin user already exists.");
    }
  }
}

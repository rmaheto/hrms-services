package org.muhikira.authservice.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.muhikira.authservice.dto.UserRequestDto;
import org.muhikira.authservice.dto.UserResponseDto;
import org.muhikira.authservice.entity.Role;
import org.muhikira.authservice.entity.User;
import org.muhikira.authservice.exception.IncorrectOldPasswordException;
import org.muhikira.authservice.exception.PasswordMismatchException;
import org.muhikira.authservice.exception.RoleNotFoundException;
import org.muhikira.authservice.exception.UserNotFoundException;
import org.muhikira.authservice.mapper.UserMapper;
import org.muhikira.authservice.model.RoleName;
import org.muhikira.authservice.model.UpdatePasswordRequest;
import org.muhikira.authservice.repository.RoleRepository;
import org.muhikira.authservice.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final PasswordEncoder passwordEncoder;

  private static final String USER_NOT_FOUND = "User not found";
  private static final String ROLE_NOT_FOUND = "Role not found";
  public void createUser(UserRequestDto userDto) {

    String encodedPassword = passwordEncoder.encode(userDto.getPassword());

    User user = new User();
    user.setUsername(userDto.getUsername());
    user.setPassword(encodedPassword);

    Set<Role> roles = new HashSet<>();
    for (RoleName roleName : userDto.getRoles()) {
      Role role =
          roleRepository
              .findByName(roleName)
              .orElseThrow(() -> new RoleNotFoundException(ROLE_NOT_FOUND));
      roles.add(role);
    }
    user.setRoles(roles);

    userRepository.save(user);
  }

  public void updatePassword(Long userId, UpdatePasswordRequest passwordRequest) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));

    if (!passwordEncoder.matches(passwordRequest.getOldPassword(), user.getPassword())) {
      throw new IncorrectOldPasswordException("Old password is incorrect");
    }

    if (!passwordRequest.getNewPassword().equals(passwordRequest.getConfirmPassword())) {
      throw new PasswordMismatchException("New password and confirm password do not match");
    }

    String encodedNewPassword = passwordEncoder.encode(passwordRequest.getNewPassword());
    user.setPassword(encodedNewPassword);

    userRepository.save(user);
  }

  public List<UserResponseDto> getAllUsers() {
    List<User> users = userRepository.findAll();
    return users.stream()
        .map(UserMapper::toUserResponseDto)
        .toList();
  }

  public UserResponseDto getUserById(Long userId) {
    User user =
        userRepository
            .findById(userId)
            .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
    return UserMapper.toUserResponseDto(user);
  }

  public UserResponseDto updateUserRoles(Long userId, Set<RoleName> newRoles) {
    User user =
        userRepository
            .findById(userId)
            .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));

    Set<Role> roles = new HashSet<>();
    for (RoleName roleName : newRoles) {
      Role role = roleRepository.findByName(roleName)
          .orElseThrow(() -> new RoleNotFoundException(ROLE_NOT_FOUND));
      roles.add(role);
    }

    user.setRoles(roles);
    User updatedUser = userRepository.save(user);

    return UserMapper.toUserResponseDto(updatedUser);
  }

  public List<UserResponseDto> getUsersByRole(String roleName) {
    RoleName roleEnum;
    try {
      roleEnum = RoleName.valueOf(roleName);
    } catch (IllegalArgumentException e) {
      throw new RoleNotFoundException("Invalid role: " + roleName);
    }

    List<User> users = userRepository.findByRoles_Name(roleEnum);

    return users.stream()
        .map(UserMapper::toUserResponseDto)
        .toList();
  }

  public List<Role> getAllRoles() {
    return roleRepository.findAll();
  }
}

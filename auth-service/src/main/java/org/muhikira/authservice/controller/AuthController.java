package org.muhikira.authservice.controller;

import lombok.RequiredArgsConstructor;
import org.muhikira.authservice.model.AuthRequest;
import org.muhikira.authservice.model.AuthResponse;
import org.muhikira.authservice.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;

  @PostMapping("/login")
  public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest) {
    String jwt =
        authService.authenticateAndGenerateToken(
            authRequest.getUsername(), authRequest.getPassword());

    return ResponseEntity.ok(new AuthResponse(jwt));
  }

}

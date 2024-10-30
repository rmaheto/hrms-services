package com.muhikira.benefitsservice.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

  private SecurityUtils() {
    // Private constructor to prevent instantiation
  }

  /**
   * Gets the username of the currently authenticated user.
   * Returns "SYSTEM" if no user is authenticated.
   *
   * @return the username of the authenticated user, or "SYSTEM" if not authenticated
   */
  public static String getCurrentUser() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    return (auth != null && auth.isAuthenticated()) ? auth.getName() : "SYSTEM";
  }
}

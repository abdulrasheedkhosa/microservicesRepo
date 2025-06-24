package com.smartfusiontech.cards;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class TestPassword {
  public static void main(String[] args) {
    String rawPassword = "admin123";
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    // Encrypt password
    String hashed = encoder.encode(rawPassword);
    System.out.println("BCrypt Hashed Password: " + hashed);

    // Verify password
    boolean matches = encoder.matches("admin123", hashed);
    System.out.println("Password matches: " + matches); // should print true


    SecureRandom secureRandom = new SecureRandom();
    byte[] keyBytes = new byte[32]; // 256-bit key
    secureRandom.nextBytes(keyBytes);
    String secretKey = Base64.getEncoder().encodeToString(keyBytes);
    System.out.println("Generated Secret Key: " + secretKey);
  }
}

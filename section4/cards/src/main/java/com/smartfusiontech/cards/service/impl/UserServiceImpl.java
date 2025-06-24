package com.smartfusiontech.cards.service.impl;

import com.smartfusiontech.cards.dto.UserLoginRequestDto;
import com.smartfusiontech.cards.dto.UserLoginResponseDto;
import com.smartfusiontech.cards.dto.UserProfileResponseDto;
import com.smartfusiontech.cards.entity.User;
import com.smartfusiontech.cards.repository.UserRepository;
import com.smartfusiontech.cards.service.IUserService;
import com.smartfusiontech.cards.util.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Service
@AllArgsConstructor
public class UserServiceImpl implements IUserService {

  private final UserRepository userRepository;
  private final BCryptPasswordEncoder passwordEncoder;
  private final JwtUtil jwtUtil;

  @Override
  public UserLoginResponseDto   login(UserLoginRequestDto requestDto) {
    User user = userRepository.findByUsername(requestDto.getUsername())
        .orElseThrow(() -> new RuntimeException("User not found"));
    if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
      throw new RuntimeException("Invalid credentials");
    }

    String token = jwtUtil.generateToken(user.getUsername());

    return new UserLoginResponseDto("200", "Login successful", token,user.getUsername());
  }
  @Override
  public UserLoginResponseDto registerUser(UserLoginRequestDto registerDto) {
    if (userRepository.findByUsername(registerDto.getUsername()).isPresent()) {
      throw new RuntimeException("Username already exists");
    }
    if (userRepository.findByEmail(registerDto.getEmail()).isPresent()) {
      throw new RuntimeException("Email already exists");
    }
    User user = new User();
    user.setUsername(registerDto.getUsername());
    user.setEmail(registerDto.getEmail());
    String haspassword = passwordEncoder.encode(registerDto.getPassword());
    System.out.println("haspassword: " + haspassword);
    user.setPassword((haspassword));
    userRepository.save(user);
    String token = jwtUtil.generateToken(user.getUsername());
    return new UserLoginResponseDto("201", "User registered successfully", token,
        user.getUsername());
  }
  @Override
  public UserProfileResponseDto getUserProfile(String username) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
     username = authentication.getName();
     System.err.println("username: " + username);
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new RuntimeException("User not found"));
    return new UserProfileResponseDto(user.getUsername(), user.getEmail(), user.getPassword());
  }
}

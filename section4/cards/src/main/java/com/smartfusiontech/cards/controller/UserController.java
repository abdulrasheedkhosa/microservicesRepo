package com.smartfusiontech.cards.controller;
import com.smartfusiontech.cards.dto.UserLoginRequestDto;
import com.smartfusiontech.cards.dto.UserLoginResponseDto;
import com.smartfusiontech.cards.dto.UserProfileResponseDto;
import com.smartfusiontech.cards.service.IUserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@RestController
@AllArgsConstructor
@Validated
public class UserController {
  private final IUserService userService;
  @PostMapping("/login")
  public ResponseEntity<UserLoginResponseDto> login(@Valid @RequestBody UserLoginRequestDto loginRequestDto) {
    return ResponseEntity.ok(userService.login(loginRequestDto));
  }
  @PostMapping("/register")
  public ResponseEntity<String> register(@Valid @RequestBody UserLoginRequestDto registerDto) {
    userService.registerUser(registerDto);
    return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
  }
  @GetMapping("/profile")
  public ResponseEntity<UserProfileResponseDto> getProfile(@RequestParam String username) {
    UserProfileResponseDto profile = userService.getUserProfile(username);
    return ResponseEntity.ok(profile);
  }
}

package com.smartfusiontech.cards.service;
import com.smartfusiontech.cards.dto.UserLoginRequestDto;
import com.smartfusiontech.cards.dto.UserLoginResponseDto;
import com.smartfusiontech.cards.dto.UserProfileResponseDto;
import org.springframework.security.core.userdetails.UserDetails;

public interface IUserService {
  UserLoginResponseDto login(UserLoginRequestDto requestDto);
  UserLoginResponseDto registerUser(UserLoginRequestDto requestDto);
  UserProfileResponseDto getUserProfile(String username);

}

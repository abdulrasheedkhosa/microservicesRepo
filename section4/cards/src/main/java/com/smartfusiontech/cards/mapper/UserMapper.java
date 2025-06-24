package com.smartfusiontech.cards.mapper;

import com.smartfusiontech.cards.dto.UserLoginRequestDto;
import com.smartfusiontech.cards.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

  public User toEntity(UserLoginRequestDto dto) {
    User user = new User();
    user.setUsername(dto.getUsername());
    user.setPassword(dto.getPassword());
    return user;
  }
}
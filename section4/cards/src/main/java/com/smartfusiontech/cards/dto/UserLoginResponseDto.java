package com.smartfusiontech.cards.dto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserLoginResponseDto {
  private String statusCode;
  private String message;
  private String token;
  private String username;

}
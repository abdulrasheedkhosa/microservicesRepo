package com.smartfusiontech.accounts.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidationDto {
  private String message;
  private Boolean isPassed;
  private String validationCode;
}

package com.smartfusiontech.cards.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class EventOrderResponseDto {
  private String name;
  private String email;
  private String mobileNumber;
  private LocalDate eventDate;
  private String eventType;
  private String address;
  private int numberOfGuests;
  private String specialRequests;
}

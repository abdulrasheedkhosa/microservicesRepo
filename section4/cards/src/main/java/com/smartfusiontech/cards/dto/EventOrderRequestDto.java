package com.smartfusiontech.cards.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class EventOrderRequestDto {
  private String name;
  private String email;
  private String mobileNumber;
  private LocalDate eventDate;
  private String eventType;
  private String address;
  private int numberOfGuests;
  private String specialRequests;
}

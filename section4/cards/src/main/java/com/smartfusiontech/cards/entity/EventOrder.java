package com.smartfusiontech.cards.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "event_orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder // ✅ Required for .builder() to work
public class EventOrder {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private String email;
  private String mobileNumber;
  private LocalDate eventDate;
  private String eventType;
  private String address;
  private int numberOfGuests;
  private String specialRequests;
}

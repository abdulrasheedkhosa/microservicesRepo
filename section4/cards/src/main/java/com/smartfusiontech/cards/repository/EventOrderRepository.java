package com.smartfusiontech.cards.repository;

import com.smartfusiontech.cards.entity.EventOrder;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventOrderRepository extends JpaRepository<EventOrder, Long> {
  Optional<EventOrder> findByMobileNumberAndEventDate(String mobileNumber, LocalDate eventDate);

}

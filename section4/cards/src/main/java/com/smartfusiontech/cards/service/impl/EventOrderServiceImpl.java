package com.smartfusiontech.cards.service.impl;
import com.smartfusiontech.cards.constants.GlobalConstants;
import com.smartfusiontech.cards.constants.OrderConstants;
import com.smartfusiontech.cards.dto.EventOrderRequestDto;
import com.smartfusiontech.cards.dto.EventOrderResponseDto;
import com.smartfusiontech.cards.dto.ResponseDto;
import com.smartfusiontech.cards.entity.EventOrder;
import com.smartfusiontech.cards.exception.OrderAlreadyExistsException;
import com.smartfusiontech.cards.repository.EventOrderRepository;
import com.smartfusiontech.cards.service.EventOrderService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EventOrderServiceImpl implements EventOrderService {

  private final EventOrderRepository orderRepository;

  @Override
  public ResponseDto createOrder(EventOrderRequestDto request) {
    // Check if order already exists for mobileNumber and eventDate
    Optional<EventOrder> existing = orderRepository.findByMobileNumberAndEventDate(
        request.getMobileNumber(), request.getEventDate()
    );

    if (existing.isPresent()) {
      throw new OrderAlreadyExistsException("⚠️ Order already exists for mobile " + request.getMobileNumber());
    }

    // Create and save new order
    EventOrder newOrder = EventOrder.builder()
        .name(request.getName())
        .email(request.getEmail())
        .mobileNumber(request.getMobileNumber())
        .eventDate(request.getEventDate())
        .eventType(request.getEventType())
        .address(request.getAddress())
        .numberOfGuests(request.getNumberOfGuests())
        .specialRequests(request.getSpecialRequests())
        .build();
    orderRepository.save(newOrder);

    return new ResponseDto(GlobalConstants.STATUS_201, OrderConstants.ORDER_CREATED_SUCCESS);
  }
  @Override
  public List<EventOrderResponseDto> getAllOrders() {
    List<EventOrder> orders = orderRepository.findAll();
    return orders.stream().map(order -> EventOrderResponseDto.builder()
        .name(order.getName())
        .email(order.getEmail())
        .mobileNumber(order.getMobileNumber())
        .eventDate(order.getEventDate())
        .eventType(order.getEventType())
        .address(order.getAddress())
        .numberOfGuests(order.getNumberOfGuests())
        .specialRequests(order.getSpecialRequests())
        .build()
    ).toList();
  }

}

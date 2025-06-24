package com.smartfusiontech.cards.service;

import com.smartfusiontech.cards.dto.EventOrderRequestDto;
import com.smartfusiontech.cards.dto.EventOrderResponseDto;
import com.smartfusiontech.cards.dto.ResponseDto;
import java.util.List;

public interface EventOrderService {
  ResponseDto createOrder(EventOrderRequestDto request);
  List<EventOrderResponseDto> getAllOrders(); // Add this method

}

package com.smartfusiontech.cards.controller;
import com.smartfusiontech.cards.constants.CardsConstants;
import com.smartfusiontech.cards.constants.GlobalConstants;
import com.smartfusiontech.cards.constants.OrderConstants;
import com.smartfusiontech.cards.dto.EventOrderRequestDto;
import com.smartfusiontech.cards.dto.EventOrderResponseDto;
import com.smartfusiontech.cards.dto.ResponseDto;
import com.smartfusiontech.cards.entity.EventOrder;
import com.smartfusiontech.cards.service.EventOrderService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@RestController
@AllArgsConstructor
@Validated
public class EventOrderController {
  private final EventOrderService orderService;
  @PostMapping("/placeOrder")
  public ResponseEntity<ResponseDto> placeOrder(@RequestBody EventOrderRequestDto request) {
    orderService.createOrder(request);
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(new ResponseDto(GlobalConstants.STATUS_201, OrderConstants.ORDER_CREATED_SUCCESS));
  }
  @GetMapping("/orders")
  public ResponseEntity<List<EventOrderResponseDto>> getAllOrders() {
    List<EventOrderResponseDto> orders = orderService.getAllOrders();
    return ResponseEntity.ok(orders);
  }

}

package com.smartfusiontech.cards.exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus(value = HttpStatus.CONFLICT) // 409
public class OrderAlreadyExistsException extends RuntimeException {
  public OrderAlreadyExistsException(String message) {
    super(message);
  }
}

package com.smartfusiontech.accounts.dto.config;

import com.smartfusiontech.accounts.dto.LogDto;
import java.time.LocalDateTime;
import org.slf4j.MDC;

  public class LogResponse {

    public static LogDto success(String message, Object data) {
      return LogDto.builder()
          .traceId(MDC.get("traceId"))
          .logMessage(message)
          .logType("INFO")
          .logDate(LocalDateTime.now())
          .input(null)
          .data(data)
          .build();
    }

    public static LogDto error(String message, Object data) {
      return LogDto.builder()
          .traceId(MDC.get("traceId"))
          .logMessage(message)
          .logType("ERROR")
          .logDate(LocalDateTime.now())
          .input(null)
          .data(data)
          .build();
    }
}

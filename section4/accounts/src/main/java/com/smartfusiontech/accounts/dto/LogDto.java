package com.smartfusiontech.accounts.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LogDto {
  public String traceId;
  public String logMessage;
  public String logDescription;
  public String logType;
  public LocalDateTime logDate;
  public String input;
  public Response output;
  public Object data;
}

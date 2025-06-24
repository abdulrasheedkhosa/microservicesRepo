package com.smartfusiontech.accounts.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponseDto implements Serializable {
  @JsonIgnore
  private String createdBy;
  @JsonIgnore
  private String updatedBy;
  @JsonIgnore
  private LocalDateTime createdOn;
  @JsonIgnore
  private LocalDateTime updatedOn;
  private boolean active;
}

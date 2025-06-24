package com.smartfusiontech.accounts.dto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.List;
import lombok.Getter;
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Response<T> extends BaseResponseDto implements Serializable {
  private boolean success = true;
  private int serviceCode;
  private List<String> messages;
  private List<ValidationDto> validations;
  // Generic response payload of APIs
  private T data;

  public Response() {
    super();
  }

}


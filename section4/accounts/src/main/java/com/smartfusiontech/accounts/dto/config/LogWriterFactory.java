package com.smartfusiontech.accounts.dto.config;

public class LogWriterFactory {

  public static <T> LogWriter<T> createLogWriter(Class<T> targetClass) {
    return new LogWriter<>(targetClass);
  }
}
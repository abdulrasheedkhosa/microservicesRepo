package com.smartfusiontech.accounts.dto.config;

import com.smartfusiontech.accounts.dto.LogDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;


public class LogWriter<T> {
  private final Logger LOGGER;

  public LogWriter(Class<T> targetClass) {
    this.LOGGER = LoggerFactory.getLogger(targetClass);
  }

  public void log(LogDto logDto) {
    String logMessage = buildLogMessage(logDto);
    LogType logType;
    try {
      logType = LogType.valueOf(logDto.getLogType().toUpperCase());
    } catch (IllegalArgumentException e) {
      logType = LogType.INFO; // Default to INFO level
    }
    switch (logType) {
      case INFO:
        LOGGER.info(logMessage);
        break;
      case DEBUG:
        LOGGER.debug(logMessage);
        break;
      case WARN:
        LOGGER.warn(logMessage);
        break;
      case ERROR:
        LOGGER.error(logMessage);
        break;
      case TRACE:
        LOGGER.trace(logMessage);
        break;
    }
  }

  public void log(LogDto logDto, Throwable throwable) {
    String logMessage = buildLogMessage(logDto);
    LogType logType;
    try {
      logType = LogType.valueOf(logDto.getLogType().toUpperCase());
    } catch (IllegalArgumentException e) {
      logType = LogType.INFO; // Default to INFO level
    }
    switch (logType) {
      case INFO:
        LOGGER.info(logMessage, throwable);
        break;
      case DEBUG:
        LOGGER.debug(logMessage, throwable);
        break;
      case WARN:
        LOGGER.warn(logMessage, throwable);
        break;
      case ERROR:
        LOGGER.error(logMessage, throwable);
        break;
      case TRACE:
        LOGGER.trace(logMessage, throwable);
        break;
    }
  }

  private String buildLogMessage(LogDto logDto) {
    return String.format(
        "Trace ID: %s, Message: %s, Description: %s, Date: %s, Input: %s, Output: %s",
        MDC.get("traceId"),
        logDto.getLogMessage() != null ? logDto.getLogMessage() : "N/A",
        logDto.getLogDescription() != null ? logDto.getLogDescription() : "N/A",
        logDto.getLogDate() != null ? logDto.getLogDate() : "N/A",
        logDto.getData() != null ? logDto.getData() : "N/A",
        logDto.getInput() != null ? logDto.getInput() : "N/A",
        logDto.getOutput() != null ? logDto.getOutput() : "N/A"
    );
  }

  public enum LogType {
    INFO, DEBUG, WARN, ERROR, TRACE
  }
  }
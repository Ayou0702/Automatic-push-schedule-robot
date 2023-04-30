package com.enterprise.util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class LogUtil {
  private static final Logger LOGGER = LoggerFactory.getLogger(LogUtil.class);

  public static Logger getLogger(Class<?> clazz) {
    return LoggerFactory.getLogger(clazz);
  }

  public static void info(String message) {
    LOGGER.info(message);
  }

  public static void warn(String message) {
    LOGGER.warn(message);
  }

  public static void error(String message) {
    LOGGER.error(message);
  }
}

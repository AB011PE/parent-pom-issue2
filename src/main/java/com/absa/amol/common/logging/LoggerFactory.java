package com.absa.amol.common.logging;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Properties;

import com.absa.amol.common.constant.UtilityConstants;
import com.absa.amol.common.util.StringUtil;

/**
 * 
 * @author AB011UM Logger factory class- load logging properties and return logger instance.
 */
public final class LoggerFactory {

  private static final Properties loggerProperties = new Properties();
  private static final org.slf4j.Logger logger =
      org.slf4j.LoggerFactory.getLogger(LoggerFactory.class);


  static {
    loadProperties();
    final String path = loggerProperties.getProperty(UtilityConstants.LOGGER_PATH);
    if (StringUtil.isStringNullOrEmpty(path)) {
      logger.error(String.format(
          " %s ERROR [%s] %s: Could not find logger path configuration in log4j.properties.",
          LocalDateTime.now(), Thread.currentThread().getName(), LoggerFactory.class.getName()));
    }
  }

  private static void loadProperties() {
    try (final InputStream inputStream =
        LoggerFactory.class.getClassLoader().getResourceAsStream("log4j.properties")) {
      try {
        loggerProperties.load(inputStream);
      } catch (final IOException e) {
        logger.error(String.format(
            " %s ERROR [%s] %s: Could not load log4j.properties. Reason - %s", LocalDateTime.now(),
            Thread.currentThread().getName(), LoggerFactory.class.getName(), e.getMessage()));
      }
    } catch (final IOException e) {
      logger.error(String.format(
          " %s ERROR [%s] %s: log4j.properties not found in the classpath. Setting to default values. Reason - %s",
          LocalDateTime.now(), Thread.currentThread().getName(), LoggerFactory.class.getName(),
          e.getMessage()));
    }

    logger.info(String.format("%s [%s] INFO %s: loggerPath = %s", LocalDateTime.now(),
        Thread.currentThread().getName(), LoggerFactory.class.getName(),
        loggerProperties.getProperty(UtilityConstants.LOGGER_PATH)));
  }

  public static Logger getLogger(final Class<?> klass) {
    return ApiLogger.getLogger(klass);
  }

  public static String getLoggerProperty(final String key, final String defaultValue) {
    return loggerProperties.getProperty(key, defaultValue);
  }

}

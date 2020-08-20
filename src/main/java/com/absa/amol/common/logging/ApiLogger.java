package com.absa.amol.common.logging;


import java.util.concurrent.ConcurrentHashMap;

import com.absa.amol.common.constant.UtilityConstants;
import com.absa.amol.common.util.CommonUtil;



/**
 * 
 * @author AB011UM Logger interface method implementation for different logging levels also creates
 *         logger instance.
 *
 */
public class ApiLogger implements Logger {

  private final static ConcurrentHashMap<Class<?>, ApiLogger> loggers =
      new ConcurrentHashMap<Class<?>, ApiLogger>();

  private final org.slf4j.Logger logger;

  private final String global_project_name = LoggerFactory
      .getLoggerProperty(UtilityConstants.PROJECT_NAME, UtilityConstants.PROJECT_NAME_NOT_FOUND);

  private ApiLogger(final Class<?> klass) {
    this.logger = org.slf4j.LoggerFactory.getLogger(klass);
  }

  static Logger getLogger(final Class<?> klass) {
    ApiLogger tempLogger = loggers.get(klass);
    if (CommonUtil.isNull(tempLogger)) {
      tempLogger = new ApiLogger(klass);
      loggers.putIfAbsent(klass, tempLogger);
    }
    return tempLogger;
  }


  @Override
  public void debug(final String method_name, final String correlation_id, final String message,
      final String value) {
    this.logger.debug("{}:{}:{}():{}:{}:{}", this.global_project_name, this.logger.getName(),
        method_name, correlation_id, message, value);
  }

  @Override
  public void debug(final String method, final String correlation_id, final String message,
      final Throwable t) {
    this.logger.debug("{}:{}:{}():{}:{}:{}", this.global_project_name, this.logger.getName(),
        method, correlation_id, message, t);
  }

  @Override
  public void debugCustom(final String messageFormat, final Object... messageObjects) {
    this.logger.debug(messageFormat, messageObjects);
  }

  @Override
  public void info(final String method_name, final String correlation_id, final String message,
      final String value) {
    this.logger.info("{}:{}:{}():{}:{}:{} ", this.global_project_name, this.logger.getName(),
        method_name, correlation_id, message, value);
  }

  @Override
  public void info(final String method, final String correlation_id, final String message,
      final Throwable t) {
    this.logger.info("{}:{}:{}():{}:{}:{}", this.global_project_name, this.logger.getName(), method,
        correlation_id, message, t);
  }

  @Override
  public void infoCustom(final String messageFormat, final Object... messageObjects) {
    this.logger.info(messageFormat, messageObjects);
  }

  @Override
  public void warn(final String method_name, final String correlation_id, final String message,
      final String value) {
    this.logger.warn("{}:{}:{}():{}:{}:{} ", this.global_project_name, this.logger.getName(),
        method_name, correlation_id, message, value);
  }

  @Override
  public void warn(final String method, final String correlation_id, final String message,
      final Throwable t) {
    this.logger.warn("{}:{}:{}():{}:{}:{} ", this.global_project_name, this.logger.getName(),
        method, correlation_id, message, t);
  }

  @Override
  public void warnCustom(final String messageFormat, final Object... messageObjects) {
    this.logger.warn(messageFormat, messageObjects);
  }

  @Override
  public void error(final String method_name, final String correlation_id, final String message,
      final String error) {
    this.logger.error("{}:{}:{}():{}:{}:{} ", this.global_project_name, this.logger.getName(),
        method_name, correlation_id, message, error);
  }

  @Override
  public void error(final String method, final String correlation_id, final String message,
      final Throwable t) {
    this.logger.error("{}:{}:{}():{}:{}:{}", this.global_project_name, this.logger.getName(),
        method, correlation_id, message, t);
  }

  @Override
  public void errorCustom(final String messageFormat, final Object... messageObjects) {
    this.logger.error(messageFormat, messageObjects);
  }

  @Override
  public void error(final Throwable t) {
    this.logger.error("Stacktrace: ", t);
  }

  @Override
  public Boolean isTraceEnabled() {
    return (this.logger.isTraceEnabled());
  }

  @Override
  public Boolean isDebugEnabled() {
    return (this.logger.isDebugEnabled());
  }

  @Override
  public Boolean isInfoEnabled() {
    return (this.logger.isInfoEnabled());
  }

  @Override
  public Boolean isWarnEnabled() {
    return (this.logger.isWarnEnabled());
  }

  @Override
  public Boolean isErrorEnabled() {
    return (this.logger.isErrorEnabled());
  }

}

package com.absa.amol.common.constant;

/**
 * 
 * @author AB011UE This class for declaring constants
 */
public final class UtilityConstants {
  private UtilityConstants() {}

  public static final String PROJECT_NAME = "project_name";

  public static final String PROJECT_NAME_NOT_FOUND = "PROJECT_NAME_NOT_FOUND";

  public static final String LOGGER_PATH = "log4j.appender.File.File";

  public static final String LOGGER_PATH_NOT_FOUND = "LOGGER PATH NOT FOUND";

  public static final String LOGGER_NODE = "loggerNode";

  public static final String ENABLE_PERF_LOG = "log4j.enablePerfLog";

  public static final String GET_METHOD = "GET";

  public static final int BUFFER_SIZE = 1000000;

  public static final String CORELATION_ID = "Correlation-Id";

  public static final String COUNTRY_CODE = "Country-Code";

  public static final String BUSINESS_ID = "Business-Id";

  public static final String SYSTEM_ID = "System-Id";

  public static final String METHOD_NOT_ALLOWED_CODE = "405";

  public static final String REQUEST_ERROR_CODE = "12345";

  public static final String REQUEST_ERROR_DESCRYPTION = "Invalid request";

  public static final String RESPONSE_ERROR_CODE = "12345";

  public static final String RESPONSE_ERROR_DESCRYPTION = "Invalid response";

  public static final String GENERIC_ERROR_CODE = "12345";

  public static final String GENERIC_RESPONSE_ERROR_DESCRYPTION = "Issue occured from application";

  public static final String PARSING_ERROR_DESCRYPTION =
      "JsonParse Exception occured while parsing json";

  public static final String IOEXCEPTION_PARSING_ERROR_DESCRYPTION =
      "IO_Exception  occured while parsing json";

  public static final String COUNTRY_CODE_EMPTY_NULL = "Country Code cannot be null or empty";

  public static final String SYSTEM_ID_EMPTY_NULL =
      "System Id/Originating Channel cannot be null or empty";
  public static final String SYSTEM_ID_PATTERN =
      "System Id/Originating Channel should contain only alphabet and 2-3 characters";

  public static final String BUSINESS_ID_EMPTY_NULL = "Business Id cannot be null or empty";
  public static final String BUSINESS_ID_SIZE = "Business Id must contain 5 characters";

  public static final String CORRELATION_ID_EMPTY_NULL = "Correlation Id cannot be null or empty";
  public static final String CORRELATION_ID_PATTERN =
      "CorrelationId should contain 1-36 characters with alphanumeric and only '-' hyphen is allowed";
}

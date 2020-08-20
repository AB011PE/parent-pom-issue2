package com.absa.amol.common.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Set;
import javax.json.Json;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;

public final class MaskingUtil {
  private static StringBuilder sbr;
  private static boolean maskRequired = false;
  private static boolean partialMaskRequired = false;

  private MaskingUtil() {}

  public static String maskSensitiveInfo(final String jsonString, final Set<String> sensativeField,
      final Set<String> partialSecureFields) {
    return maskSensitiveInfo(new ByteArrayInputStream(jsonString.getBytes()), sensativeField,
        partialSecureFields);
  }

  /**
   * this method to parse and create json for masking
   * 
   * @param jsonInputStream
   * @param sensativeField
   * @return
   */
  public static String maskSensitiveInfo(InputStream jsonInputStream,
      final Set<String> sensativeField, final Set<String> partialSecureFields) {

    sbr = new StringBuilder();
    try (JsonParser parser = Json.createParser(jsonInputStream);) {
      String key = null;
      while (parser.hasNext()) {
        final Event event = parser.next();
        switch (event) {
          case KEY_NAME:
            key = parser.getString();
            maskRequired = sensativeField.contains(key);
            partialMaskRequired = partialSecureFields.contains(key);
            sbr.append(String.format("\"%s\":", key));
            break;
          case VALUE_STRING:
            addValue(String.format("\"%s\"", parser.getString()));
            break;
          case VALUE_NUMBER:
            addValue(parser.getBigDecimal());
            break;
          case VALUE_TRUE:
            addValue(true);
            break;
          case VALUE_FALSE:
            addValue(false);
            break;
          case VALUE_NULL:
            addValue(null);
            break;
          case START_OBJECT:
            sbr.append("{");
            break;
          case END_OBJECT:
            sbr.delete(sbr.lastIndexOf(","), sbr.length());
            sbr.append("},");
            break;
          case START_ARRAY:
            sbr.append("[");
            break;
          case END_ARRAY:
            sbr.delete(sbr.lastIndexOf(","), sbr.length());
            sbr.append("],");
            break;
        }
      }
    }
    sbr.delete(sbr.lastIndexOf(","), sbr.length());
    return String.valueOf(sbr);
  }

  /**
   * This method to add value in string builder
   * 
   * @param <T>
   * @param value
   */
  private static <T> void addValue(final T value) {
    if (maskRequired || partialMaskRequired) {
      String maskValue = maskValue(value, 0, false);
      sbr.append(maskValue + ",");

    } else {
      sbr.append(value + ",");
    }
  }

  /**
   * below method to mask string partial or full
   * 
   * @param <T>
   * @param value
   * @param digit
   * @param maskFromFront
   * @return
   */
  @SuppressWarnings("unused")
  private static <T> String maskValue(final T value, int digit, final boolean maskFromFront) {
    String stringVal = "";
    String number = convertToString(value);
    if (partialMaskRequired && number.length() > 4) {
      stringVal = maskString(number, 1, number.length() - 5, '*');
    } else {
      stringVal = maskString(number, 1, number.length() - 1, '*');
    }
    return stringVal;
  }

  /**
   * this method to covert into String from generic
   * 
   * @param <T>
   * @param value
   * @return converted String
   */
  public static <T> String convertToString(final T value) {

    if (value instanceof String) {
      return value + "";
    }
    return String.format("\"%s\"", value.toString());
  }

  /**
   * this method to mask complete string
   * 
   * @param strText
   * @param start
   * @param end
   * @param maskChar
   * @return
   */
  public static String maskString(String strText, int start, int end, char maskChar) {

    if (StringUtil.isStringNullOrEmpty(strText)) {
      return "";
    }

    if (start < 0) {
      start = 0;
    }

    if (end > strText.length()) {
      end = strText.length();
    }

    int maskLength = end - start;

    if (maskLength == 0) {
      return strText;
    }

    StringBuilder sbMaskString = new StringBuilder(maskLength);

    for (int i = 0; i < maskLength; i++) {
      sbMaskString.append(maskChar);
    }

    return strText.substring(0, start) + sbMaskString.toString()
        + strText.substring(start + maskLength);
  }

}

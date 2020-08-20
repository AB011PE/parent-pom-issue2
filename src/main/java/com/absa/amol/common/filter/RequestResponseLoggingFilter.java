package com.absa.amol.common.filter;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Set;
import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import com.absa.amol.common.constant.UtilityConstants;
import com.absa.amol.common.exception.ApiRequestException;
import com.absa.amol.common.exception.ApiResponseException;
import com.absa.amol.common.logging.Logger;
import com.absa.amol.common.logging.LoggerFactory;
import com.absa.amol.common.util.CollectionUtil;
import com.absa.amol.common.util.CommonUtil;
import com.absa.amol.common.util.MaskingUtil;
import com.absa.amol.common.util.StringUtil;

/**
 * @author AB011UE this class is for logging request and response
 */
@Provider
public class RequestResponseLoggingFilter
    implements ContainerRequestFilter, ContainerResponseFilter {

  @Context
  private Configuration configuration;


  @Inject
  @ConfigProperty(name = "secureFields", defaultValue = "${ sdsd}")
  Set<String> secureFields;

  @Inject
  @ConfigProperty(name = "partialSecureFields", defaultValue = "${ sdsd}")
  Set<String> partialSecureFields;

  /**
   * Logger instance
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(RequestResponseLoggingFilter.class);

  /**
   * ContainerRequestContext holds the instance from request below filter logs info and does masking
   * sensetive information
   */
  @Override
  public void filter(ContainerRequestContext reqContext) throws IOException, ApiRequestException {

    // Extracting header info
    HashMap<String, String> headers = extractMap(reqContext.getHeaders());



    // corelationId need to extract from header from payload
    String corelationId = headers.get(UtilityConstants.CORELATION_ID);
    LOGGER.info("filter", corelationId, "REQUEST- PATH IS", reqContext.getUriInfo().getPath());
    LOGGER.info("filter", corelationId, "REQUEST-HEADER-RECIEVED ",
        headers.toString() + " END OF HEADERS");

    LOGGER.debug("filter", corelationId, "SECURE-FIELD-RECIEVED ",
        secureFields.toString() + " END OF HEADERS");

    LOGGER.debug("filter", corelationId, "PARTIOAL-SECURE-FIELD-RECIEVED ",
        partialSecureFields.toString() + " END OF HEADERS");

    // Extracting queryParam info
    HashMap<String, String> queryParam = extractMap(reqContext.getUriInfo().getQueryParameters());

    if (CollectionUtil.isNotNullAndNotEmpty(queryParam)) {
      createSecureQueryParam(queryParam);
      LOGGER.info("filter", corelationId, "QUERY-PARAMETERS-RECIEVED ", queryParam.toString());

    }
    // if request is "get method"
    if (StringUtil.isStringNotNullAndNotEmpty(reqContext.getMethod())) {

      if (UtilityConstants.GET_METHOD.equalsIgnoreCase(reqContext.getMethod())) {
        LOGGER.info("filter", corelationId, "REQUEST-RECIEVED ", "no payload");
      } else {
        logRequestWithPayload(reqContext, corelationId);
      }
    } else {
      LOGGER.error("filter", corelationId, "REQUEST-RECIEVED ", "no request method found");
    }
  }

  /**
   * This is to log response returned from API
   * 
   * ContainerResponseContext holds the instance from request below filter logs info and does
   * masking sensetive information
   * 
   */
  @Override
  public void filter(ContainerRequestContext reqContext, ContainerResponseContext responseContext)
      throws ApiResponseException {

    // Extracting header info
    HashMap<String, String> headers = extractMap(reqContext.getHeaders());

    // corelationId need to extract from header from payload
    String corelationId = headers.get(UtilityConstants.CORELATION_ID);

    // check for responsebody
    if (CommonUtil.isNull(responseContext.getEntity())) {
      LOGGER.info("filter", corelationId, "RESPONSE- SENT ", "reponse payload is null");
    } else {
      logResponse(responseContext, corelationId);
    }
  }


  /**
   * this method is going to log the request
   * 
   * @param reqContext
   * @param corelationId
   */
  private void logRequestWithPayload(ContainerRequestContext reqContext, String corelationId) {
    BufferedInputStream stream = new BufferedInputStream(reqContext.getEntityStream());

    String payload = null;
    try {
      stream.mark(UtilityConstants.BUFFER_SIZE);
      StringBuilder stringBuilder = new StringBuilder();
      String line = null;
      BufferedReader bufferedReader =
          new BufferedReader(new InputStreamReader(stream, Charset.defaultCharset()));
      while ((line = bufferedReader.readLine()) != null) {
        stringBuilder.append(line);
      }
      payload = stringBuilder.toString();
      if (StringUtil.isStringNotNullAndNotEmpty(payload)) {
        String securedPayload =
            MaskingUtil.maskSensitiveInfo(payload, secureFields, partialSecureFields);

        LOGGER.info("filter", corelationId, "REQUEST-RECIEVED ",
            securedPayload + " END OF REQUEST");
      } else {
        LOGGER.info("filter", corelationId, "REQUEST-RECIEVED ", "Payload is null");
      }
      stream.reset();
    } catch (Exception ex) {

      LOGGER.error("filter", corelationId, "Issue in reading request", ex.getMessage());
      LOGGER.debug("filter", corelationId, "Issue in reading request", ex);
      // commented timebeng not to raise issue as masking utility is in testing phase
      // throw new ApiRequestException(UtilityConstants.REQUEST_ERROR_CODE,
      // UtilityConstants.REQUEST_ERROR_DESCRYPTION);
    }
    reqContext.setEntityStream(stream);
  }

  /**
   * This method is going to log response
   * 
   * @param resContext
   * @param corelationID
   * @throws ApiResponseException
   */
  private void logResponse(ContainerResponseContext resContext, String corelationID)
      throws ApiResponseException {

    // extracting response string from responseContext
    String payload = "";


    LOGGER.debug("filter", corelationID, "", "Inside responsefilter");
    try {
      if (CommonUtil.isNotNull(resContext.getEntity())) {
        Jsonb jsonb = JsonbBuilder.create();
        payload = jsonb.toJson(resContext.getEntity());
        // Creating and logging secure Payload
        if (StringUtil.isStringNotNullAndNotEmpty(payload)) {
          String securedPayload =
              MaskingUtil.maskSensitiveInfo(payload, secureFields, partialSecureFields);

          LOGGER.info("filter", corelationID, "RESPONSE-SENT ",
              securedPayload + " END OF RESPONSE");
        } else {
          LOGGER.info("filter", corelationID, "RESPONSE-SENT ", "Payload is null");
        }
      } else {
        LOGGER.info("filter", corelationID, "RESPONSE-SENT ", "Payload is null");
      }
    } catch (Exception ex) {
      LOGGER.error("filter", corelationID, "Issue in logging response", ex.getMessage());
      LOGGER.debug("filter", corelationID, "Issue in logging response", ex);
    }
  }


  /**
   * This method is to extract header values
   * 
   * @param uriInfo uriInfo denotes info of URI request
   * 
   * @param headers headers denotes complete header to be extracted
   * @return It returns hashmap contains header in request received
   */
  private HashMap<String, String> extractMap(MultivaluedMap<String, ?> map) {

    HashMap<String, String> mapParam = new HashMap<>();

    if (CollectionUtil.isNotNullAndNotEmpty(map)) {
      for (String str : map.keySet()) {
        mapParam.put(str, (String) map.getFirst(str));
      }
    }

    return mapParam;
  }



  /**
   * Method to create secure Query param
   * 
   * @param queryParam
   */
  private void createSecureQueryParam(HashMap<String, String> queryParam) {

    Set<String> set = queryParam.keySet();
    String masked = "";
    for (String key : set) {

      if (secureFields.contains(key)) {

        String value = queryParam.get(key);
        String strVal = MaskingUtil.convertToString(value);
        masked = MaskingUtil.maskString(strVal, 0, strVal.length(), '*');
        queryParam.put(key, masked);
      } else if (partialSecureFields.contains(key)) {
        String value = queryParam.get(key);
        String strVal = MaskingUtil.convertToString(value);
        if (strVal.length() > 4) {
          masked = MaskingUtil.maskString(strVal, 0, strVal.length() - 4, '*');
        } else {
          masked = MaskingUtil.maskString(strVal, 0, strVal.length(), '*');
        }
        queryParam.put(key, masked);
      }

    }
  }
}

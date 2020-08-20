package com.absa.amol.common.filter;

import javax.ws.rs.NotAllowedException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import com.absa.amol.common.constant.UtilityConstants;
import com.absa.amol.common.exception.ApiRequestException;
import com.absa.amol.common.exception.ApiResponseException;
import com.absa.amol.common.exception.DownStreamSystemUnavailableException;
import com.absa.amol.common.exception.GlobalException;
import com.absa.amol.common.logging.Logger;
import com.absa.amol.common.logging.LoggerFactory;
import com.absa.amol.common.model.ResponseEntity;

/**
 * @author AB011UE ExceptionFilter is to process all exception and assemble response to calling
 *         channel
 * 
 */
@Provider
public class ExceptionFilter implements ExceptionMapper<Exception> {

  /**
   * logger for exception logging
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionFilter.class);

  /**
   * This is method to catch all exception raised from API
   * 
   * Response contains ApiExceptionMessage instance having all info of exception occured.
   */
  @Override
  public Response toResponse(Exception ex) {
    @SuppressWarnings("rawtypes")
    ResponseEntity responseEntity = null;
    try {
      throw ex;
    } catch (ApiRequestException reqEx) {
      responseEntity = new ResponseEntity<ApiRequestException>(reqEx.getErrorCode(),
          reqEx.getErrorMessage(), "Failure", null);
      return Response.status(Response.Status.BAD_REQUEST).entity(responseEntity)
          .type("application/json").build();
    } catch (ApiResponseException resExp) {
      responseEntity = new ResponseEntity<ApiResponseException>(resExp.getErrorCode(),
          resExp.getErrorMessage(), "Failure", null);

      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseEntity)
          .type("application/json").build();
    } catch (DownStreamSystemUnavailableException resExp) {
      responseEntity = new ResponseEntity<DownStreamSystemUnavailableException>(
          resExp.getErrorCode(), resExp.getErrorMessage(), "Failure", null);

      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseEntity)
          .type("application/json").build();
    } catch (GlobalException resExp) {
      responseEntity = new ResponseEntity<GlobalException>(resExp.getErrorCode(),
          resExp.getErrorMessage(), "Failure", null);

      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseEntity)
          .type("application/json").build();
    } catch (NotAllowedException resExp) {
      responseEntity = new ResponseEntity<NotAllowedException>(
          UtilityConstants.METHOD_NOT_ALLOWED_CODE, resExp.getMessage(), "Failure", null);

      return Response.status(Response.Status.METHOD_NOT_ALLOWED).entity(responseEntity)
          .type("application/json").build();
    } catch (Exception exce) {
      responseEntity = new ResponseEntity<Exception>(null, exce.getMessage(), "Failure", null);
      LOGGER.error("Exceptionfilter", "", "Exception occured inside application",
          exce.getMessage());
      LOGGER.debug("Exceptionfilter", "", "Exception occured inside application", exce);
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseEntity)
          .type("application/json").build();

    }
  }

}

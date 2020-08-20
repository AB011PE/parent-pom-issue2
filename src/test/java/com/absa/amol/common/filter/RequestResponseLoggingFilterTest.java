package com.absa.amol.common.filter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import com.absa.amol.common.exception.ApiException;
import com.absa.amol.common.exception.ApiRequestException;
import com.absa.amol.common.exception.ApiResponseException;
import com.absa.amol.common.exception.DownStreamSystemUnavailableException;
import com.absa.amol.common.exception.GlobalException;
import com.absa.amol.common.model.ResponseEntity;

public class RequestResponseLoggingFilterTest {

  @Mock
  private Configuration configuration;

  @Mock
  ContainerRequestContext containerRequestContext;

  @Mock
  ContainerResponseContext containerResponseContext;

  @Mock
  private UriInfo urlInfo;

  @Mock
  private MultivaluedMap<String, String> map;

  @InjectMocks
  private RequestResponseLoggingFilter requestResponseLoggingFilter;

  @BeforeEach
  public void beforeEach() {
    MockitoAnnotations.initMocks(this);
  }

  /**
   * It is to test get methods in request
   * 
   * @throws IOException
   */
  // @Test
  public void requestFilterGetMethodTest() throws IOException {
    Mockito.when(configuration.getProperties()).thenReturn(new HashMap<String, Object>());
    Mockito.when(urlInfo.getPath()).thenReturn("/abc/def/ghi");
    Mockito.when(containerRequestContext.getUriInfo()).thenReturn(urlInfo);
    Mockito.when(containerRequestContext.getHeaders()).thenReturn(map);
    Mockito.when(containerRequestContext.getMethod()).thenReturn("GET");
    requestResponseLoggingFilter.filter(containerRequestContext);
  }

  /**
   * 
   * It is to test successful logging of an request
   * 
   * @throws IOException
   */

  /*
   * @Test public void requestFilterPostMethodTest() throws IOException {
   * 
   * 
   * Mockito.when(configuration.getProperties()).thenReturn(new HashMap<String, Object>());
   * Mockito.when(urlInfo.getPath()).thenReturn("/abc/def/ghi");
   * Mockito.when(containerRequestContext.getUriInfo()).thenReturn(urlInfo);
   * Mockito.when(containerRequestContext.getHeaders()).thenReturn(map);
   * Mockito.when(containerRequestContext.getMethod()).thenReturn("POST"); String initialString =
   * "{\"phonetype\":\"N95\",\"cat\":\"WP\"}"; InputStream targetStream = new
   * ByteArrayInputStream(initialString.getBytes()); //
   * containerRequestContext.setEntityStream(targetStream);
   * Mockito.when(containerRequestContext.getEntityStream()).thenReturn(targetStream);
   * requestResponseLoggingFilter.filter(containerRequestContext);
   * 
   * }
   */

  /**
   * requestFilterPostMethodExceptionTest for raising an exception from requestfilter
   * 
   * 
   * @throws IOException
   */
  // @Test
  public void requestFilterPostMethodExceptionTest() throws IOException {


    Mockito.when(configuration.getProperties()).thenReturn(new HashMap<String, Object>());
    Mockito.when(urlInfo.getPath()).thenReturn("/abc/def/ghi");
    Mockito.when(containerRequestContext.getUriInfo()).thenReturn(urlInfo);
    Mockito.when(containerRequestContext.getHeaders()).thenReturn(map);
    Mockito.when(containerRequestContext.getMethod()).thenReturn("POST");
    String initialString = "{\"phonetype\":\"N95\",\"cat\":\"WP\"}";
    InputStream targetStream = new ByteArrayInputStream(initialString.getBytes());
    Assertions.assertThrows(ApiException.class, () -> {
      requestResponseLoggingFilter.filter(containerRequestContext);
    });

  }

  /**
   * It is for successful logging of response entity
   * 
   * @throws IOException
   */

  @Test
  public void responseFilterMethodTest() throws IOException {
    Mockito.when(configuration.getProperties()).thenReturn(new HashMap<String, Object>());
    Mockito.when(urlInfo.getPath()).thenReturn("/abc/def/ghi");
    String initialString = "{\"phonetype\":\"N95\",\"cat\":\"WP\"}";
    InputStream targetStream = new ByteArrayInputStream(initialString.getBytes());
    ResponseEntity responseEntity = new ResponseEntity("200", "", "Sucess", null);
    containerResponseContext.setEntity(responseEntity);
    Mockito.when(containerResponseContext.getEntity()).thenReturn(responseEntity);
    requestResponseLoggingFilter.filter(containerRequestContext, containerResponseContext);
  }

  /**
   * To test null of response entity
   * 
   * @throws ApiRequestException
   * @throws IOException
   */
  @Test
  public void responseFilterMethodNullTest() throws ApiRequestException, IOException {
    Mockito.when(configuration.getProperties()).thenReturn(new HashMap<String, Object>());
    Mockito.when(urlInfo.getPath()).thenReturn("/abc/def/ghi");
    Mockito.when(containerResponseContext.getEntity()).thenReturn(null);
    requestResponseLoggingFilter.filter(containerRequestContext, containerResponseContext);
  }

  /**
   * To Raise and test ApiResponseException
   * 
   * @throws IOException
   */
  @Test
  public void responseFilterMethodApiResponseExceptionTest() throws IOException {
    Mockito.when(configuration.getProperties()).thenReturn(new HashMap<String, Object>());
    Mockito.when(urlInfo.getPath()).thenReturn("/abc/def/ghi");
    Mockito.when(containerResponseContext.getEntity()).thenThrow(ApiResponseException.class);
    Assertions.assertThrows(ApiException.class, () -> {
      requestResponseLoggingFilter.filter(containerRequestContext, containerResponseContext);
    });
  }

  /**
   * To raise and test GlobalException
   * 
   * @throws IOException
   */
  @Test
  public void responseFilterMethodGlobalExceptioTest() throws IOException {
    Mockito.when(configuration.getProperties()).thenReturn(new HashMap<String, Object>());
    Mockito.when(urlInfo.getPath()).thenReturn("/abc/def/ghi");
    Mockito.when(containerResponseContext.getEntity()).thenThrow(GlobalException.class);
    Assertions.assertThrows(GlobalException.class, () -> {
      requestResponseLoggingFilter.filter(containerRequestContext, containerResponseContext);
    });
  }

  /**
   * To Test DownStreamSystemUnavailableException
   * 
   * @throws IOException
   */
  @Test
  public void responseFilterMethodDownstearmExceptioTest() throws IOException {
    Mockito.when(configuration.getProperties()).thenReturn(new HashMap<String, Object>());
    Mockito.when(urlInfo.getPath()).thenReturn("/abc/def/ghi");
    Mockito.when(containerResponseContext.getEntity())
        .thenThrow(DownStreamSystemUnavailableException.class);
    Assertions.assertThrows(DownStreamSystemUnavailableException.class, () -> {
      requestResponseLoggingFilter.filter(containerRequestContext, containerResponseContext);
    });
  }
}

package com.absa.amol.common.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.ws.rs.HeaderParam;
import com.absa.amol.common.constant.UtilityConstants;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ApiRequestHeader {
  @HeaderParam(UtilityConstants.COUNTRY_CODE)
  @NotNull(message = UtilityConstants.COUNTRY_CODE_EMPTY_NULL)
  @NotEmpty(message = UtilityConstants.COUNTRY_CODE_EMPTY_NULL)
  private String countryCode;

  @HeaderParam(UtilityConstants.SYSTEM_ID)
  @NotNull(message = UtilityConstants.SYSTEM_ID_EMPTY_NULL)
  @NotEmpty(message = UtilityConstants.SYSTEM_ID_EMPTY_NULL)
  @Pattern(regexp = "[A-Za-z]{2,3}", message = UtilityConstants.SYSTEM_ID_PATTERN)
  private String systemId;

  @HeaderParam(UtilityConstants.BUSINESS_ID)
  @NotNull(message = UtilityConstants.BUSINESS_ID_EMPTY_NULL)
  @NotEmpty(message = UtilityConstants.BUSINESS_ID_EMPTY_NULL)
  private String businessId;

  @HeaderParam(UtilityConstants.CORELATION_ID)
  @NotNull(message = UtilityConstants.CORRELATION_ID_EMPTY_NULL)
  @NotEmpty(message = UtilityConstants.CORRELATION_ID_EMPTY_NULL)
  @Pattern(regexp = "[a-zA-Z\\-0-9]{1,36}", message = UtilityConstants.CORRELATION_ID_PATTERN)
  private String correlationId;

}

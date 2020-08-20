package com.absa.amol.common.exception;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author AB011UE
 *this is response exception while calling to SOR or proccessing
 */
@Getter
@Setter
@ToString
public class ApiResponseException extends ApiException {

	private static final long serialVersionUID = 1L;
	private String errorCode;
	private String errorMessage;

	public ApiResponseException(String errorCode, String errorMessage) {
		super();
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

}

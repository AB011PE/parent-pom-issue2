package com.absa.amol.common.exception;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author AB011UE 
 * 
 * ApiRequestException class is for issues in request
 */
@Getter
@Setter
@ToString
public class GlobalException extends ApiException {

	private static final long serialVersionUID = 1L;
	
	private String errorCode;
	private String errorMessage;

	public GlobalException(String errorCode, String errorMessage) {
		super();
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}
}

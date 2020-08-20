package com.absa.amol.common.logging;
/**
 * 
 * Logger interface - abstract methods of logging for different level
 */
public interface Logger {

	void debug(String method_name, String correlation_id, String message, String value);

	void debug(String method, String correlation_id, String message, Throwable t);

	void debugCustom(String messageFormat, Object... messageObjects);
	

	void info(String method_name, String correlation_id, String message, String value);
	
	void info(String method, String correlation_id, String message, Throwable t);

	void infoCustom(String messageFormat, Object... messageObjects);
	

	void warn(String method_name, String correlation_id, String message, String value);

	void warn(String method, String correlation_id, String message, Throwable t);

	void warnCustom(String messageFormat, Object... messageObjects);
	

	void error(String method_name, String correlation_id, String message, String error);

	void error(String method, String correlation_id, String message, Throwable t);

	void errorCustom(String messageFormat, Object... messageObjects);

	void error(Throwable t);

	Boolean isTraceEnabled();

	Boolean isDebugEnabled();

	Boolean isInfoEnabled();

	Boolean isWarnEnabled();

	Boolean isErrorEnabled();

}
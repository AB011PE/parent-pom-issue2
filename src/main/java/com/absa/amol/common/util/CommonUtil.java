package com.absa.amol.common.util;

public final class CommonUtil {
	private CommonUtil() {}
	
	/**
	 * To check object whether object null or not, return true if object is null else false.
	 * @param t object
	 * @return boolean
	 */
    public static <T>boolean isNull(T t){
        return t==null;
    }
    
    /**
	 * To check object whether object null or not, return true if object is not null else false.
	 * @param t object
	 * @return boolean
	 */
    public static <T>boolean isNotNull(T t){
        return t!=null;
    }
    
    
}

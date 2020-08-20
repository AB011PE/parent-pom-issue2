package com.absa.amol.common.util;

import static com.absa.amol.common.util.CommonUtil.isNotNull;
import static com.absa.amol.common.util.CommonUtil.isNull;

public class StringUtil {

	/**
   	 * To check object whether String null or not, return true if String is not null and not empty else false.
   	 * @param t String object
   	 * @return boolean
   	 */
    public static boolean isStringNotNullAndNotEmpty(String t){
        return (isNotNull(t) && !t.isEmpty());
    }
    
    /**
   	 * To check object whether String null or not, return true if String is null or empty else false.
   	 * @param t String object
   	 * @return boolean
   	 */
    public static boolean isStringNullOrEmpty(String t){
        return (isNull(t) || t.isEmpty());
    }
    
    /**
   	 * return string length, return 0 if string is null or empty.
   	 * @param t String object
   	 * @return int
   	 */
    public static int getStringLength(String t){
        return isStringNotNullAndNotEmpty(t)?t.length():0;
    }
}

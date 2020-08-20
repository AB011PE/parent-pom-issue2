package com.absa.amol.common.util;

import static com.absa.amol.common.util.CommonUtil.isNotNull;
import static com.absa.amol.common.util.CommonUtil.isNull;

import java.util.Collection;
import java.util.Map;

public class CollectionUtil {


    /**
   	 * To check object whether Collection null or not, return true if collection is not null and not empty else false.
   	 * @param t Collection object
   	 * @return boolean
   	 */
    public static <T>boolean isNotNullAndNotEmpty(Collection<?> t){
        return (isNotNull(t) && !t.isEmpty());
    }
    
    /**
   	 * To check object whether Collection null or not, return true if collection is null or empty else false.
   	 * @param t Collection object
   	 * @return boolean
   	 */
    public static <T>boolean isNullOrEmpty(Collection<?> t){
        return (isNull(t) || t.isEmpty());
    }
    
    
    /**
   	 * To check object whether Map null or not, return true if Map is not null and not empty else false.
   	 * @param t Map object Object
   	 * @return boolean
   	 */
    public static <T>boolean isNotNullAndNotEmpty(Map<?,?> t){
        return (isNotNull(t) && !t.isEmpty());
    }
    
    /**
   	 * To check object whether Map null or not, return true if Map is null or empty else false.
   	 * @param t Map object Object
   	 * @return boolean
   	 */
    public static <T>boolean isNullOrEmpty(Map<?,?> t){
        return (isNull(t) || t.isEmpty());
    }
    
}

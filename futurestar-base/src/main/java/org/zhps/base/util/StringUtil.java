package org.zhps.base.util;

/**
 * Copyright (c) 2012 Conversant Solutions. All rights reserved.
 * <p/>
 * Created on 2017/4/12.
 */
public class StringUtil {
    /**
     *
     * @param values
     * @return
     */
    public static String assembleString(String... values){
        if(values == null || values.length == 0){
            return null;
        }
        StringBuilder str = new StringBuilder();
        for(String value : values){
            str.append(value);
        }
        return str.toString();
    }
}

package org.zhps.trader.util;

import org.zhps.hjctp.jni.NativeLoader;

/**
 * Copyright (c) 2012 Conversant Solutions. All rights reserved.
 * <p/>
 * Created on 2017/1/12.
 */
public class QueryUtil {
    public static int queryTradingAccount(){
        return NativeLoader.queryTradingAccount();
    }

    public static int queryInvestorPosition(){
        return 0;
    }

    public static int queryInvestorPositionDetail(){
        return 0;
    }
}

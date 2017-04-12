package org.zhps.market.storage;

import org.hbase.async.HBaseClient;
import org.zhps.base.hbase.BaseHbase;

/**
 * Copyright (c) 2012 Conversant Solutions. All rights reserved.
 * <p/>
 * Created on 2017/4/12.
 */
public class DbStorage {
    private static final HBaseClient hBaseClient = BaseHbase.gethBaseClient();

    /**
     *
     * @param lastNum
     * @return
     */
    public static Double readLastHighestPrice(int lastNum){
        return 0d;
    }

    public static Double readLastLowestPrice(int lastNum){
        return 0d;
    }
}

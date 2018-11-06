package org.zhps.market.data;

import org.zhps.market.entity.Quotation;
import org.zhps.market.storage.MemStorage;

/**
 * Copyright (c) 2012 Conversant Solutions. All rights reserved.
 * <p/>
 * Created on 2017/4/12.
 */
public class D1Line {

    public static void produce(Quotation quotation){
        String updateTime = quotation.getUpdateTime();
        quotation.setHour(Integer.parseInt(updateTime.split(":")[0]));
        quotation.setMinute(Integer.parseInt(updateTime.split(":")[1]));
        quotation.setSecond(Integer.parseInt(updateTime.split(":")[2]));

        store(quotation);
    }

    public static void produce(String instrumentId, String markets){
        MemStorage.storage(instrumentId, markets);
    }

    private static void store(Quotation quotation){
        MemStorage.storage(quotation);
    }
}

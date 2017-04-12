package org.zhps.market.storage;

import org.zhps.base.redis.BaseRedis;
import org.zhps.base.util.StringUtil;
import org.zhps.market.entity.Quotation;
import redis.clients.jedis.Jedis;

/**
 * Copyright (c) 2012 Conversant Solutions. All rights reserved.
 * <p/>
 * Created on 2017/4/12.
 */
public class MemStorage {
    private static final Jedis jedis = BaseRedis.getJedis();

    public static void storage(Quotation quotation){
        jedis.set(StringUtil.assembleString(quotation.getInstrumentId(),"_q"),
                StringUtil.assembleString(
                        String.valueOf(quotation.getLastPrice()), "|",
                        String.valueOf(quotation.getHour()), "|",
                        String.valueOf(quotation.getMinute()), "|",
                        String.valueOf(quotation.getSecond()), "|",
                        String.valueOf(quotation.getHighestPrice()), "|",
                        String.valueOf(quotation.getLowestPrice()), "|",
                        String.valueOf(quotation.getLastHighest()), "|",
                        String.valueOf(quotation.getLastLowest())));
    }
}

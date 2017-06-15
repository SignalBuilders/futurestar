package org.zhps.market.storage;

import org.zhps.base.redis.BaseRedis;
import org.zhps.base.util.StringUtil;
import org.zhps.market.entity.F5mQuotation;
import org.zhps.market.entity.Quotation;
import redis.clients.jedis.Jedis;

/**
 * Copyright (c) 2012 Conversant Solutions. All rights reserved.
 * <p/>
 * Created on 2017/4/12.
 */
public class MemStorage {
    private static final Jedis jedis = BaseRedis.getJedis();

    public static void storage(Quotation quotation, F5mQuotation f5mQuotation){
        jedis.set(StringUtil.assembleString(quotation.getInstrumentId(),"_q"),
                StringUtil.assembleString(
                        String.valueOf(quotation.getHour()), "|",
                        String.valueOf(quotation.getMinute()), "|",
                        String.valueOf(quotation.getSecond()), "|",
                        String.valueOf(f5mQuotation.getOpen()), "|",
                        String.valueOf(quotation.getLatestPrice()), "|",
                        String.valueOf(f5mQuotation.getHighest()), "|",
                        String.valueOf(f5mQuotation.getLowest()), "|",
                        String.valueOf(f5mQuotation.getCave5ml()), "|",
                        String.valueOf(f5mQuotation.getCave10ml()), "|",
                        String.valueOf(f5mQuotation.getLave5ml()), "|",
                        String.valueOf(f5mQuotation.getLave10ml())));
    }
}

package org.zhps.market.storage;

import org.apache.commons.io.IOUtils;
import org.zhps.base.redis.BaseRedis;
import org.zhps.base.util.PropertiesUtil;
import org.zhps.base.util.StringUtil;
import org.zhps.market.entity.F5mQuotation;
import org.zhps.market.entity.Quotation;
import redis.clients.jedis.Jedis;

import java.util.Date;

/**
 * Copyright (c) 2012 Conversant Solutions. All rights reserved.
 * <p/>
 * Created on 2017/4/12.
 */
public class MemStorage {
//    private static final Jedis jedis = BaseRedis.getJedis();

    public static void storage(Quotation quotation){
        Jedis jedis = BaseRedis.getJedis();
        jedis.set(StringUtil.assembleString(quotation.getInstrumentId(),"_q"),
                StringUtil.assembleString(
                        String.valueOf(quotation.getHour()), "|",
                        String.valueOf(quotation.getMinute()), "|",
                        String.valueOf(quotation.getSecond()), "|",
                        String.valueOf(quotation.getOpenPrice()), "|",
                        String.valueOf(quotation.getLatestPrice()), "|",
                        String.valueOf(quotation.getHighestPrice()), "|",
                        String.valueOf(quotation.getLowestPrice()), "|",
                        String.valueOf(quotation.getInterest()), "|",
                        String.valueOf(quotation.getVolume())));
    }

    public static void storage(String instrumentId, String markets){
        try(Jedis jedis = BaseRedis.getJedis()){
            jedis.set(StringUtil.assembleString(instrumentId,"_q"),markets);
        }catch (Exception e){
            System.out.println("storage contract q error|error:|" + e.getMessage() + "date:" + new Date());
        }finally {
//            IOUtils.closeQuietly(jedis);
        }
    }
}

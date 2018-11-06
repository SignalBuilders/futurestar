package org.zhps.df.util;

import org.zhps.base.redis.BaseRedis;
import redis.clients.jedis.Jedis;

import java.util.Calendar;

/**
 * Copyright (c) 2012 Conversant Solutions. All rights reserved.
 * <p>
 * Created on 2017/9/23.
 */
public class ContractUtil {
    final static Jedis jedis = BaseRedis.getJedis();

    public static String[] getContractGroup(String variety){
        String contracts = jedis.get(variety);
        if(contracts != null){
            return contracts.split(",");
        }else{
            return new String[0];
        }
    }
}

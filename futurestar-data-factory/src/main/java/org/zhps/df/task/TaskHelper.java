package org.zhps.df.task;

import org.zhps.base.redis.BaseRedis;
import org.zhps.base.util.PropertiesUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Copyright (c) 2012 Conversant Solutions. All rights reserved.
 * <p/>
 * Created on 2016/12/28.
 */
public class TaskHelper {
    public static void openMarket(){
        Jedis jedis = BaseRedis.getJedis();
    }

    public static void closeMarket(){

    }
}

package org.zhps.base.redis;

import org.zhps.base.util.PropertiesUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Copyright (c) 2012 Conversant Solutions. All rights reserved.
 * <p/>
 * Created on 2016/12/28.
 */
public class BaseRedis {
    static JedisPool jedisPool = null;
    static {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(PropertiesUtil.REDIS_POOL_TOTAL);
        config.setMaxIdle(PropertiesUtil.REDIS_POOL_IDLE);
        config.setMaxWaitMillis(PropertiesUtil.REDIS_POOL_WAIT_MILLIS);
        config.setTestOnBorrow(PropertiesUtil.REDIS_POOL_BORROW);
        config.setTestOnReturn(PropertiesUtil.REDIS_POOL_RETURN);
        jedisPool = new JedisPool(config, PropertiesUtil.REDIS_IP, PropertiesUtil.REDIS_PORT, 1000);
    }

    public static Jedis getJedis(){
        return BaseRedis.jedisPool.getResource();
    }
}

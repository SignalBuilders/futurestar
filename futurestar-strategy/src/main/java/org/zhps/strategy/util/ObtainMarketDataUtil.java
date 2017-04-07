package org.zhps.strategy.util;

import org.zhps.base.redis.BaseRedis;
import org.zhps.strategy.vo.QuotationVO;
import org.zhps.strategy.vo.StrategyVO;
import redis.clients.jedis.Jedis;

import java.util.regex.Pattern;

/**
 * Copyright (c) 2012 Conversant Solutions. All rights reserved.
 * <p/>
 * Created on 2017/4/7.
 */
public class ObtainMarketDataUtil {
    Jedis jedis = BaseRedis.getJedis();
    private static final Pattern VERTICAL = Pattern.compile("\\|");

    public StrategyVO obtain(){
        //traday|hour|minute|second|open|highest|lowest|last|ave5d|ave10d|ave3m5l|ave3m10l
        StrategyVO strategyVO = new StrategyVO();
        QuotationVO quotationVO = new QuotationVO();
        String values = jedis.get("current");
        if(values != null){
            String[] array = VERTICAL.split(values);

        }

        return strategyVO;
    }
}

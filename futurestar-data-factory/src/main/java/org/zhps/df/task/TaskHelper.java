package org.zhps.df.task;

import com.stumbleupon.async.Callback;
import com.stumbleupon.async.Deferred;
import org.hbase.async.Config;
import org.hbase.async.GetRequest;
import org.hbase.async.HBaseClient;
import org.hbase.async.KeyValue;
import org.zhps.base.hbase.BaseHbase;
import org.zhps.base.redis.BaseRedis;
import org.zhps.base.util.PropertiesUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.ArrayList;
import java.util.concurrent.Executors;

/**
 * Copyright (c) 2012 Conversant Solutions. All rights reserved.
 * <p/>
 * Created on 2016/12/28.
 */
public class TaskHelper {
    public static void openMarket(){
        Jedis jedis = BaseRedis.getJedis();
//        System.out.println(jedis.get("test"));

        HBaseClient hbaseClient = BaseHbase.gethBaseClient();

        GetRequest getRequest = new GetRequest("test", "row2");
        try {
            ArrayList<KeyValue> kvs = hbaseClient.get(getRequest).join();
            System.out.println(kvs);
            hbaseClient.shutdown().joinUninterruptibly();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void closeMarket(){

    }

    public static void main(String[] args) {
        openMarket();
    }
}

package org.zhps.base.hbase;

import org.hbase.async.Config;
import org.hbase.async.HBaseClient;
import org.zhps.base.util.PropertiesUtil;

import java.util.concurrent.Executors;

/**
 * Copyright (c) 2012 Conversant Solutions. All rights reserved.
 * <p/>
 * Created on 2016/12/29.
 */
public class BaseHbase {
    static HBaseClient hBaseClient = null;
    static {
        Config config = new Config();
        config.overrideConfig("hbase.zookeeper.quorum", PropertiesUtil.HBASE_ZOOKEEPER_QUORUM);
        config.overrideConfig("hbase.rpcs.buffered_flush_interval", PropertiesUtil.HBASE_BUFFERED_FLUSH_INTERVAL);
        config.overrideConfig("hbase.rpcs.batch.size", PropertiesUtil.HBASE_BATCH_SIZE);
        hBaseClient = new HBaseClient(config, Executors.newCachedThreadPool());
    }

    public static HBaseClient gethBaseClient(){
        return BaseHbase.hBaseClient;
    }
}

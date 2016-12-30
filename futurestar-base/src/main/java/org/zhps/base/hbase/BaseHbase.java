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
        config.overrideConfig("hbase.region_client.inflight_limit", "1");
        config.overrideConfig("hbase.rpc.timeout", "50000");
        config.overrideConfig("hbase.zookeeper.session.timeout", "500000");
        hBaseClient = new HBaseClient(config, Executors.newCachedThreadPool());
    }

    public static HBaseClient gethBaseClient(){
        return BaseHbase.hBaseClient;
    }
}

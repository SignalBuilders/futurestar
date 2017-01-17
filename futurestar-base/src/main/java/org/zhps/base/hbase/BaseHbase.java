package org.zhps.base.hbase;

import com.stumbleupon.async.Callback;
import org.hbase.async.Config;
import org.hbase.async.GetRequest;
import org.hbase.async.HBaseClient;
import org.hbase.async.KeyValue;
import org.zhps.base.util.PropertiesUtil;

import java.util.ArrayList;
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

//    public static void main(String[] args) {
//        GetRequest getPropery = new GetRequest("test", "row1");
//        try {
//            hBaseClient.get(getPropery).addCallback(new Callback<Object, ArrayList<KeyValue>>() {
//                @Override
//                public Object call(ArrayList<KeyValue> keyValues) throws Exception {
//                    Thread.sleep(5000);
//                    System.out.println(keyValues);
//                    return new String("aaa");
//                }
//            });
//            for(KeyValue k : values){
//                System.out.println(new String(k.key()) + "==" + new String(k.value()));
//            }
//            hBaseClient.shutdown().addCallback(new Callback<Object, Object>() {
//                @Override
//                public Object call(Object o) throws Exception {
//                    System.out.println(o);
//                    return null;
//                }
//            }).joinUninterruptibly();
//            System.out.println(hBaseClient.stats().flushes());
//
//            System.out.println(123);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}

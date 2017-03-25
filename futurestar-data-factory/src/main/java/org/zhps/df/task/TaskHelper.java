package org.zhps.df.task;

import com.stumbleupon.async.Callback;
import com.stumbleupon.async.Deferred;
import org.hbase.async.*;
import org.zhps.base.hbase.BaseHbase;
import org.zhps.base.redis.BaseRedis;
import org.zhps.base.task.BaseTask;
import org.zhps.base.util.PropertiesUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import scala.Int;

import java.util.ArrayList;
import java.util.TimerTask;
import java.util.concurrent.Executors;

/**
 * Copyright (c) 2012 Conversant Solutions. All rights reserved.
 * <p/>
 * Created on 2016/12/28.
 */
public class TaskHelper {

    public static void openMarket(){
        final HBaseClient hbaseClient = BaseHbase.gethBaseClient();
        final Jedis jedis = BaseRedis.getJedis();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                double sum5d = 0;
                double sum10d = 0;
                double ave5d = 0;
                double ave10d = 0;
                Scanner scanner = hbaseClient.newScanner(PropertiesUtil.HBASE_TABLE_CLOSE);
                try {
                    ArrayList<ArrayList<KeyValue>> tds = scanner.nextRows(9).join();
                    for(int i = 0; i < tds.size(); i++){
//                        System.out.println(tds);
                        for(KeyValue c : tds.get(i)){
                            if(i == 0 && new String(c.qualifier()).equalsIgnoreCase("ave5d")){
                                ave5d = Double.parseDouble(new String(c.value()));
                            }
                            if(i == 0 && new String(c.qualifier()).equalsIgnoreCase("ave10d")){
                                ave10d = Double.parseDouble(new String(c.value()));
                            }
                            if(new String(c.qualifier()).equalsIgnoreCase("last")){
                                if(i < PropertiesUtil.MK_AVE_5D){
                                    sum5d += Double.parseDouble(new String(c.value()));
                                }
                                sum10d += Double.parseDouble(new String(c.value()));
                            }
                        }
                    }
//                    System.out.println("sum5d: " + sum5d + " sum10d: " + sum10d + " ave5d: " + ave5d + " ave10d: " + ave10d);
                    StringBuilder value = new StringBuilder(jedis.get("traday")).append("|").append(sum5d)
                            .append("|").append(sum10d).append("|").append(ave5d).append("|").append(ave10d);
                    jedis.set("open", value.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        BaseTask.startTask(task, PropertiesUtil.OPEN_MARKET_DATE, PropertiesUtil.HOURS_12);
    }

    public static void closeMarket(){
        final HBaseClient hbaseClient = BaseHbase.gethBaseClient();
        final Jedis jedis = BaseRedis.getJedis();
        TimerTask task = new TimerTask() {

            @Override
            public void run() {
                String value = jedis.get("close");
                String[] values = value.split("\\|");
                String day = values[0];
                String time = values[1];
                String open = values[2];
                String highest = values[3];
                String lowest = values[4];
                String last = values[5];
                String ave5d = values[6];
                String ave10d = values[7];
                String volume = values[8];
                String interest = values[9];
                byte[][] colBytes = {"open".getBytes(),"highest".getBytes(),"lowest".getBytes(),"last".getBytes()
                        ,"ave5d".getBytes(),"ave10d".getBytes(),"volume".getBytes(),"interest".getBytes()};
                byte[][] valBytes = {open.getBytes(),highest.getBytes(),lowest.getBytes(),last.getBytes()
                        , ave5d.getBytes(), ave10d.getBytes(), volume.getBytes(), interest.getBytes()};
                if(time.equalsIgnoreCase("15")){
                    PutRequest put = new PutRequest(PropertiesUtil.HBASE_TABLE_CLOSE.getBytes(), ("rm|" + (100000000 - Integer.parseInt(day)) + "|" + day).getBytes(),
                            PropertiesUtil.HBASE_TABLE_CLOSE_CF.getBytes(), colBytes, valBytes);
                    hbaseClient.put(put);
                }
            }
        };
        BaseTask.startTask(task, PropertiesUtil.CLOSE_MARKET_DATE, PropertiesUtil.HOURS_24);
    }

    public static void main(String[] args) {
//        openMarket();
//        closeMarket();
//        HBaseClient hbaseClient = BaseHbase.gethBaseClient();
//        byte[][] colBytes = {"open".getBytes(),"highest".getBytes(),"lowest".getBytes(),"last".getBytes(),"ave5d".getBytes(),"ave10d".getBytes()};
//        byte[][] valBytes = {"2415".getBytes(), "2435".getBytes(), "2406".getBytes(), "2421".getBytes(), "2452".getBytes(), "2434".getBytes()};
//        PutRequest put = new PutRequest(PropertiesUtil.HBASE_TABLE_CLOSE.getBytes(), ("rm|" + (100000000 - 20170309) + "|20170309").getBytes(),
//                PropertiesUtil.HBASE_TABLE_CLOSE_CF.getBytes(), colBytes, valBytes);
//        hbaseClient.put(put);

//        System.out.println("rm|" + (100000000 - 20150202) + "|20150202");
//        System.out.println("rm|" + (100000000 - 20151231) + "|20151231");
//        System.out.println(100000000 - 20150101);
//
//
//        byte[][] valBytes1 = {"2290".getBytes(), "2295".getBytes(), "2306".getBytes()};
//        put = new PutRequest(PropertiesUtil.HBASE_TABLE_CLOSE.getBytes(), ("rm|" + (100000000 - 20170109)).getBytes(),
//                PropertiesUtil.MK_VARIETIES[PropertiesUtil.MK_VARIETY_RM].getBytes(), colBytes, valBytes1);
//        hbaseClient.put(put);
//
//        DeleteRequest delete = new DeleteRequest(PropertiesUtil.HBASE_TABLE_CLOSE.getBytes(), "20170109".getBytes(),
//                PropertiesUtil.MK_VARIETIES[PropertiesUtil.MK_VARIETY_RM].getBytes(), colBytes);
//        hbaseClient.delete(delete);
//        delete = new DeleteRequest(PropertiesUtil.HBASE_TABLE_CLOSE.getBytes(), "20170109".getBytes(),
//                PropertiesUtil.MK_VARIETIES[PropertiesUtil.MK_VARIETY_RM].getBytes(), colBytes);
//        hbaseClient.delete(delete);

//        Scanner scanner = hbaseClient.newScanner(PropertiesUtil.HBASE_TABLE_CLOSE.getBytes());
//        String key = null;
//        String ave10d = null;
//        String ave5d = null;
//        String last = null;
//        String[] keySp = null;
//        try {
//            ArrayList<ArrayList<KeyValue>> rows = scanner.nextRows().join();
//            for(ArrayList<KeyValue> kvs : rows){
//                for(KeyValue kv : kvs){
//                    if(new String(kv.qualifier()).equalsIgnoreCase("ave10d")){
//                        ave10d = new String(kv.value());
//                        key = new String(kv.key());
//                    }
//                    if(new String(kv.qualifier()).equalsIgnoreCase("ave5d")){
//                        ave5d = new String(kv.value());
//                    }
//                    if(new String(kv.qualifier()).equalsIgnoreCase("last")){
//                        last = new String(kv.value());
//                    }
////                    System.out.println(new String(kv.key()) + "--" + new String(kv.qualifier()) + "--" + new String(kv.value()));
//                }
////                System.out.println("key: " + key + " ave10d: " + ave10d + " ave5d: " + ave5d + " last: " + last);
//                keySp = key.split("\\|");
////                key = keySp[0] + "|" + keySp[1] + "|" + (100000000 - Long.valueOf(keySp[1]));
////                byte[][] valBytes = {last.getBytes(), ave5d.getBytes(), ave10d.getBytes()};
////                PutRequest put = new PutRequest(PropertiesUtil.HBASE_TABLE_CLOSE.getBytes(), key.getBytes(),
////                        PropertiesUtil.HBASE_TABLE_CLOSE_CF.getBytes(), colBytes, valBytes);
////                hbaseClient.put(put);
//                key = keySp[0] + "|" + keySp[1];
//                DeleteRequest delete = new DeleteRequest(PropertiesUtil.HBASE_TABLE_CLOSE.getBytes(), key.getBytes(),
//                PropertiesUtil.MK_VARIETIES[PropertiesUtil.MK_VARIETY_RM].getBytes(), colBytes);
//                hbaseClient.delete(delete);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }


    }
}

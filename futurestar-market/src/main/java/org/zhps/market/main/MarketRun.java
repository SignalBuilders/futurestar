package org.zhps.market.main;

import org.hbase.async.KeyValue;
import org.hbase.async.Scanner;
import org.zhps.base.redis.BaseRedis;
import org.zhps.base.task.BaseTask;
import org.zhps.base.util.PropertiesUtil;
import org.zhps.hjctp.api.MdApi;
import org.zhps.market.producer.MarketProducer;
import org.zhps.market.spi.MdSpiAdapter;
import org.zhps.market.util.WriteMarketToFileUtil;
import org.zhps.market.util.WriteTradingDayToHbase;
import redis.clients.jedis.Jedis;

import java.util.*;

/**
 * Copyright (c) 2012 Conversant Solutions. All rights reserved.
 * <p/>
 * Created on 2016/12/6.
 */
public class MarketRun {
    public static final Map<String, MdApi> mdApiMap = new HashMap<String, MdApi>();

    public static void main(String[] args) {
//        try(Jedis jedis = BaseRedis.getJedis()){
//            String[] rbContracts = jedis.get(PropertiesUtil.MK_VARIETIES[PropertiesUtil.MK_VARIETY_RB]).split(",");
        final MdApi mdApi = new MdApi(PropertiesUtil.MK_FLOW_PATH, true, true);
        mdApiMap.put("mdApi", mdApi);

        final MdSpiAdapter mdSpiA = new MdSpiAdapter(new MarketProducer());
        mdApi.registerSpi(mdSpiA);
        mdApi.registerFront(PropertiesUtil.MK_PROD);
        mdApi.registerLoginInfo("", "", "");
        mdApi.registerSubMarketData(PropertiesUtil.MK_CONTRACTS, PropertiesUtil.MK_SUB_NUM);
//            mdApi.registerSubMarketData(rbContracts, rbContracts.length);
//            startSchedule(mdApi, jedis);
        mdApi.connect();
//        }catch (Exception e){
//            System.out.println("market run error|error:" + e.getMessage());
//        }
    }

//    public static void main(String[] args) {
////        try(Jedis jedis = BaseRedis.getJedis()){
////            String[] rbContracts = jedis.get(PropertiesUtil.MK_VARIETIES[PropertiesUtil.MK_VARIETY_RB]).split(",");
//            String[] contracts = new String[PropertiesUtil.MK_SUB_NUM];
//            for(int i = 0; i < PropertiesUtil.MK_SUB_NUM; i++){
//                contracts[i] = PropertiesUtil.MK_VARIETIES[i];
//            }
//            final MdApi mdApi = new MdApi(PropertiesUtil.MK_FLOW_PATH, true, true);
//            mdApiMap.put("mdApi", mdApi);
//
//            final MdSpiAdapter mdSpiA = new MdSpiAdapter(new MarketProducer());
//            mdApi.registerSpi(mdSpiA);
//            mdApi.registerFront(PropertiesUtil.MK_PROD);
//            mdApi.registerLoginInfo("", "", "");
////        mdApi.registerSubMarketData(PropertiesUtil.MK_CONTRACTS, PropertiesUtil.MK_SUB_NUM);
//            mdApi.registerSubMarketData(contracts, contracts.length);
////            startSchedule(mdApi, jedis);
//            mdApi.connect();
////        }catch (Exception e){
////            System.out.println("market run error|error:" + e.getMessage());
////        }
//    }

    private static void startSchedule(final MdApi mdApi, final Jedis jedis){
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                System.out.println("schedule+++++++++++++++++++++" + new Date());
                mdApiMap.get("mdApi").stop();

                System.out.println("stop+++++++++++++++++++++");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("start+++++++++++++++++++++");

                new Thread(){
                    @Override
                    public void run() {
                        String[] rbContracts = jedis.get(PropertiesUtil.MK_VARIETIES[PropertiesUtil.MK_VARIETY_RB]).split(",");
                        final MdApi mdApi = new MdApi(PropertiesUtil.MK_FLOW_PATH, true, true);
                        mdApiMap.put("mdApi", mdApi);
                        final MdSpiAdapter mdSpiA = new MdSpiAdapter(new MarketProducer());
                        mdApi.registerSpi(mdSpiA);
                        mdApi.registerFront(PropertiesUtil.MK_PROD);
                        mdApi.registerLoginInfo("", "", "");
                        mdApi.registerSubMarketData(rbContracts, rbContracts.length);
                        mdApi.connect();

                        System.out.println("thread close-----------------------------------");
                    }
                }.start();
            }
        };
        BaseTask.startTask(task, PropertiesUtil.OPEN_MARKET_DATE, PropertiesUtil.HOURS_72);
//        BaseTask.startTask(task, 5000l, 10000l);
    }
}

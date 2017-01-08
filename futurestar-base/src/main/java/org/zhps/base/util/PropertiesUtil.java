package org.zhps.base.util;


import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.Properties;

/**
 * Copyright (c) 2012 Conversant Solutions. All rights reserved.
 * <p/>
 * Created on 2016/12/21.
 */
public class PropertiesUtil {
    //market
    public static String MK_SIM_TEST;
    public static String MK_SIM_FIRM;
    public static String MK_PROD;
    public static String[] MK_CONTRACTS;
    public static int MK_SUB_NUM;
    public static String MK_FLOW_PATH;
    public static int MK_QUO_INSTRUMENTID;
    public static int MK_QUO_LASTPRICE;
    public static int MK_QUO_OPENPRICE;
    public static int MK_QUO_UPPERLIMITPRICE;
    public static int MK_QUO_LOWERLIMITPRICE;
    public static int MK_QUO_UPDATETIME;
    public static int MK_QUO_TRADINGDAY;
    public static int MK_QUO_UPDATETIMEMILLISEC;
    //kafka
    public static String BOOTSTRAP_SERVERS;
    public static String ACKS;
    public static int RETRIES;
    public static int BATCH_SIZE;
    public static int LINGER_MS;
    public static long BUFFER_MEMORY;
    public static String KEY_SERIALIZER;
    public static String VALUE_SERIALIZER;
    public static String ZOOKEEPER_QUORUM;
    public static String CONSUMER_GROUP_ID;
    public static String MK_TOPIC;
    //task interval
    public static long HOURS_24 = 24 * 60 * 60 * 1000l;
    public static long HOURS_12 = 12 * 60 * 60 * 1000l;
    //task date
    public static Date OPEN_MARKET_DATE;
    public static Date CLOSE_MARKET_DATE;
    //redis
    public static String REDIS_IP;
    public static int REDIS_PORT;
    public static int REDIS_POOL_TOTAL;
    public static int REDIS_POOL_IDLE;
    public static long REDIS_POOL_WAIT_MILLIS;
    public static boolean REDIS_POOL_BORROW;
    public static boolean REDIS_POOL_RETURN;
    //hbase
    public static String HBASE_ZOOKEEPER_QUORUM;
    public static String HBASE_BUFFERED_FLUSH_INTERVAL;
    public static String HBASE_BATCH_SIZE;

    static {
        Properties prop = new Properties();
        InputStream is = Objects.class.getResourceAsStream("/config.properties");
        try {
            prop.load(is);
            MK_SIM_TEST = prop.getProperty("mk_sim_test");
            MK_SIM_FIRM = prop.getProperty("mk_sim_firm");
            MK_PROD = prop.getProperty("mk_prod");
            MK_CONTRACTS = prop.getProperty("mk_contracts").split(",");
            MK_SUB_NUM = Integer.parseInt(prop.getProperty("mk_sub_num"));
            MK_FLOW_PATH = prop.getProperty("mk_flow_path");
            MK_TOPIC = prop.getProperty("mk_topic");
            MK_QUO_INSTRUMENTID = Integer.parseInt(prop.getProperty("mk_quotation_instrumentId"));
            MK_QUO_LASTPRICE = Integer.parseInt(prop.getProperty("mk_quotation_lastPrice"));
            MK_QUO_OPENPRICE = Integer.parseInt(prop.getProperty("mk_quotation_openPrice"));
            MK_QUO_UPPERLIMITPRICE = Integer.parseInt(prop.getProperty("mk_quotation_upperLimitPrice"));
            MK_QUO_LOWERLIMITPRICE = Integer.parseInt(prop.getProperty("mk_quotation_lowerLimitPrice"));
            MK_QUO_UPDATETIME = Integer.parseInt(prop.getProperty("mk_quotation_updateTime"));
            MK_QUO_TRADINGDAY = Integer.parseInt(prop.getProperty("mk_quotation_tradingDay"));
            MK_QUO_UPDATETIMEMILLISEC = Integer.parseInt(prop.getProperty("mk_quotation_updateTimeMillisec"));

            BOOTSTRAP_SERVERS = prop.getProperty("bootstrap_servers");
            ACKS = prop.getProperty("acks");
            RETRIES = Integer.parseInt(prop.getProperty("retries"));
            BATCH_SIZE = Integer.parseInt(prop.getProperty("batch_size"));
            LINGER_MS = Integer.parseInt(prop.getProperty("linger_ms"));
            BUFFER_MEMORY = Long.parseLong(prop.getProperty("buffer_memory"));
            KEY_SERIALIZER = prop.getProperty("key_serializer");
            VALUE_SERIALIZER = prop.getProperty("value_serializer");
            ZOOKEEPER_QUORUM = prop.getProperty("zookeeper_quorum");
            CONSUMER_GROUP_ID = prop.getProperty("consumer_group_id");

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, 20);
            calendar.set(Calendar.MINUTE, 50);
            calendar.set(Calendar.SECOND, 0);
            OPEN_MARKET_DATE = calendar.getTime();

            calendar.set(Calendar.HOUR_OF_DAY, 15);
            calendar.set(Calendar.MINUTE, 30);
            calendar.set(Calendar.SECOND, 0);
            CLOSE_MARKET_DATE = calendar.getTime();

            REDIS_IP = prop.getProperty("redis_ip");
            REDIS_PORT = Integer.parseInt(prop.getProperty("redis_port"));
            REDIS_POOL_TOTAL = Integer.parseInt(prop.getProperty("redis_pool_total"));
            REDIS_POOL_IDLE = Integer.parseInt(prop.getProperty("redis_pool_idle"));
            REDIS_POOL_WAIT_MILLIS = Long.parseLong(prop.getProperty("redis_pool_wait_millis"));
            REDIS_POOL_BORROW = Boolean.parseBoolean(prop.getProperty("redis_pool_borrow"));
            REDIS_POOL_RETURN = Boolean.parseBoolean(prop.getProperty("redis_pool_return"));

            HBASE_ZOOKEEPER_QUORUM = prop.getProperty("hbase_zookeeper_quorum");
            HBASE_BUFFERED_FLUSH_INTERVAL = prop.getProperty("hbase_buffered_flush_interval");
            HBASE_BATCH_SIZE = prop.getProperty("hbase_batch_size");

        } catch (IOException e) {
            //// TODO: 2016/12/21 add log 
            e.printStackTrace();
        }finally {
            IOUtils.closeQuietly(is);
        }
    }
}

package org.zhps.util;


import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

/**
 * Copyright (c) 2012 Conversant Solutions. All rights reserved.
 * <p/>
 * Created on 2016/12/21.
 */
public class PropertiesUtil {
    //kafka param name
    final public static String BOOTSTRAP_SERVERS_NAME = "bootstrap.servers";
    final public static String ACKS_NAME = "acks";
    final public static String RETRIES_NAME = "retries";
    final public static String BATCH_SIZE_NAME = "batch.size";
    final public static String LINGER_MS_NAME = "linger.ms";
    final public static String BUFFER_MEMORY_NAME = "buffer.memory";
    final public static String KEY_SERIALIZER_NAME = "key.serializer";
    final public static String VALUE_SERIALIZER_NAME = "value.serializer";
    //market-base
    public static String MK_SIM_TEST;
    public static String MK_SIM_FIRM;
    public static String MK_PROD;
    public static String[] MK_CONTRACTS;
    public static int MK_SUB_NUM;
    public static String MK_FLOW_PATH;
    //market-kafka
    public static String MK_TOPIC;
    //kafka
    public static String BOOTSTRAP_SERVERS;
    public static String ACKS;
    public static int RETRIES;
    public static int BATCH_SIZE;
    public static int LINGER_MS;
    public static long BUFFER_MEMORY;
    public static String KEY_SERIALIZER;
    public static String VALUE_SERIALIZER;
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

            BOOTSTRAP_SERVERS = prop.getProperty("bootstrap_servers");
            ACKS = prop.getProperty("acks");
            RETRIES = Integer.parseInt(prop.getProperty("retries"));
            BATCH_SIZE = Integer.parseInt(prop.getProperty("batch_size"));
            LINGER_MS = Integer.parseInt(prop.getProperty("linger_ms"));
            BUFFER_MEMORY = Long.parseLong(prop.getProperty("buffer_memory"));
            KEY_SERIALIZER = prop.getProperty("key_serializer");
            VALUE_SERIALIZER = prop.getProperty("value_serializer");
        } catch (IOException e) {
            //// TODO: 2016/12/21 add log 
            e.printStackTrace();
        }finally {
            IOUtils.closeQuietly(is);
        }
    }
}

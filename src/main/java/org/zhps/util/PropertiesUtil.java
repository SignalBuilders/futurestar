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
    public static String MK_SIM_TEST;
    public static String MK_SIM_FIRM;
    public static String[] MK_CONTRACTS;
    public static int MK_SUB_NUM;
    public static String MK_FLOW_PATH;
    static {
        Properties prop = new Properties();
        InputStream is = Objects.class.getResourceAsStream("/config.properties");
        try {
            prop.load(is);
            MK_SIM_TEST = prop.getProperty("mk_sim_test");
            MK_SIM_FIRM = prop.getProperty("mk_sim_firm");
            MK_CONTRACTS = prop.getProperty("mk_contracts").split(",");
            MK_SUB_NUM = Integer.parseInt(prop.getProperty("mk_sub_num"));
            MK_FLOW_PATH = prop.getProperty("mk_flow_path");
        } catch (IOException e) {
            //// TODO: 2016/12/21 add log 
            e.printStackTrace();
        }finally {
            IOUtils.closeQuietly(is);
        }
    }
}

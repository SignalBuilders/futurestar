package org.zhps.trader.main;

import org.zhps.base.util.PropertiesUtil;
import org.zhps.hjctp.api.TraderApi;
import org.zhps.hjctp.spi.TraderSpi;
import org.zhps.trader.spi.TraderSpiAdapter;

/**
 * Copyright (c) 2012 Conversant Solutions. All rights reserved.
 * <p/>
 * Created on 2017/1/8.
 */
public class TraderRun {
    public static void main(String[] args) {
        TraderApi traderApi = new TraderApi(PropertiesUtil.MK_FLOW_PATH);
        TraderSpi traderSpi = new TraderSpiAdapter();
        traderApi.registerSpi(traderSpi);
        traderApi.registerFront(PropertiesUtil.TD_SIM_FIRM);
        traderApi.registerLoginInfo("9999","077580","hp120416");
        traderApi.connect();
    }
}

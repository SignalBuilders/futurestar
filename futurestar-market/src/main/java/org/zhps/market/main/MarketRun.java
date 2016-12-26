package org.zhps.market.main;

import org.zhps.base.util.PropertiesUtil;
import org.zhps.hjctp.api.MdApi;
import org.zhps.market.producer.MarketProducer;
import org.zhps.market.spi.MdSpiAdapter;

/**
 * Copyright (c) 2012 Conversant Solutions. All rights reserved.
 * <p/>
 * Created on 2016/12/6.
 */
public class MarketRun {
    public static void main(String[] args) {
        final MdApi mdApi = new MdApi(PropertiesUtil.MK_FLOW_PATH, true, true);
        final MdSpiAdapter mdSpiA = new MdSpiAdapter(new MarketProducer());
//        WriteMarketToFileUtil.startTimer(mdApi);
//        WriteMarketToFileUtil.registerMdSpi(mdSpiA);

        mdApi.registerMdSpi(mdSpiA);
        mdApi.registerFront(PropertiesUtil.MK_SIM_TEST);
        mdApi.registerLoginInfo("", "", "");
        mdApi.registerSubMarketData(PropertiesUtil.MK_CONTRACTS, PropertiesUtil.MK_SUB_NUM);
        mdApi.connect();
    }
}

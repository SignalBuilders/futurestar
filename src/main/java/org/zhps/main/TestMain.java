package org.zhps.main;

import org.hjctp.api.MdApi;
import org.zhps.market.spi.MdSpiAdapter;
import org.zhps.util.PropertiesUtil;

/**
 * Copyright (c) 2012 Conversant Solutions. All rights reserved.
 * <p/>
 * Created on 2016/12/6.
 */
public class TestMain {
    public static void main(String[] args) {
        final MdApi mdApi = new MdApi(PropertiesUtil.MK_FLOW_PATH, true, true);
        MdSpiAdapter mdSpiA = new MdSpiAdapter();
//        WriteUtil.startTimer(mdApi);
//        WriteUtil.registerMdSpi(mdSpiA);

        mdApi.registerMdSpi(mdSpiA);
        mdApi.registerFront(PropertiesUtil.MK_SIM_FIRM);
        mdApi.registerLoginInfo("", "", "");
        mdApi.registerSubMarketData(PropertiesUtil.MK_CONTRACTS, PropertiesUtil.MK_SUB_NUM);
        mdApi.connect();

//        new Thread(){
//            @Override
//            public void run() {
//                System.out.println("thread start...");
//                try {
//                    Thread.sleep(10000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                System.out.println("close.....");
//                mdApi.stop();
//            }
//        }.start();  forward quotation
    }
}

package org.zhps.trader.main;

import org.zhps.base.util.PropertiesUtil;
import org.zhps.hjctp.api.TraderApi;
import org.zhps.hjctp.entity.Qorder;
import org.zhps.hjctp.spi.TraderSpi;
import org.zhps.trader.spi.TraderSpiAdapter;

/**
 * Copyright (c) 2012 Conversant Solutions. All rights reserved.
 * <p/>
 * Created on 2017/1/8.
 */
public class TraderRun {
    public static void main(String[] args) {
        final TraderApi traderApi = new TraderApi(PropertiesUtil.MK_FLOW_PATH);
        new Thread(){
            @Override
            public void run() {
                TraderSpi traderSpi = new TraderSpiAdapter();
                traderApi.registerSpi(traderSpi);
                traderApi.registerFront(PropertiesUtil.TD_SIM_TEST);
                traderApi.registerLoginInfo(PropertiesUtil.TD_BROKER_ID,PropertiesUtil.TD_ACCOUNT_ID,PropertiesUtil.TD_PASSWORD);
                traderApi.connect();
            }
        }.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(){
            @Override
            public void run() {
                traderApi.queryTradingAccount();
//                System.out.println(traderApi.queryInvestorPositionDetail());
//                System.out.println(traderApi.queryInvestorPosition());
//                Qorder qorder = new Qorder();
//                traderApi.queryOrder(qorder);
            }
        }.start();
    }
}

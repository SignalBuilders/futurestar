package org.zhps.trader.main;

import org.zhps.base.util.PropertiesUtil;
import org.zhps.hjctp.api.TraderApi;
import org.zhps.hjctp.entity.Iorder;
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
                insert(traderApi);
//                traderApi.queryTradingAccount();

//                System.out.println(traderApi.queryInvestorPositionDetail());

                System.out.println(traderApi.queryInvestorPosition());

//                query(traderApi);

            }
        }.start();
    }

    private static void query(TraderApi traderApi){
        Qorder qorder = new Qorder();
        traderApi.queryOrder(qorder);
    }

    private static void insert(TraderApi traderApi){
        Iorder iorder = new Iorder();
        iorder.setInstrumentID("RM705");
        iorder.setOrderRef("");
        //0.buy, 1.sell
        iorder.setDirection(PropertiesUtil.TD_DIRECTION_BUY);
        //0.open, 1.close, 3.closeToday
        iorder.setCombOffsetFlag(PropertiesUtil.TD_OFFSET_FLAG_CLOSE);
        //1.Speculation
        iorder.setCombHedgeFlag(PropertiesUtil.TD_HEDGE_FLAG_SPECULATION);
        iorder.setVolumeTotalOriginal(1);
        //1.immediately
        iorder.setContingentCondition(PropertiesUtil.TD_CONTINGENT_CONDITION_IMMEDIATELY);
        //1.any, 2.min, 3.all
        iorder.setVolumeCondition(PropertiesUtil.TD_VOLUME_CONDITION_ANY);
        //1.ioc, 2.gfs, 3.gfd
        iorder.setTimeCondition(PropertiesUtil.TD_TIME_CONDITION_GFD);
        iorder.setMinVolume(1);
        //0.notForceClose, 1.lackDeposit, 2.clientOverPositionLimit, 3.memberOverPositionLimit, 4.notMultiple 5.violation, 6.other 7.personDeliv
        iorder.setForceCloseReason(PropertiesUtil.TD_FORCE_CLOSE_REASON_NOT_FORCE_CLOSE);
        iorder.setIsAutoSuspend(0);
        iorder.setUserForceClose(0);
        //1.AnyPrice, 2.LimitPrice, 3.BestPrice, 4.LastPrice
        iorder.setOrderPriceType(PropertiesUtil.TD_ORDER_PRICE_TYPE_LIMIT_PRICE);
        iorder.setLimitPrice(2464);
        traderApi.insertOrder(iorder);
    }
}

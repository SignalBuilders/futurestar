package org.zhps.trader.spi;

import org.zhps.hjctp.entity.CThostFtdcRspInfoField;
import org.zhps.hjctp.entity.CThostFtdcRspUserLoginField;
import org.zhps.hjctp.spi.TraderSpi;

/**
 * Copyright (c) 2012 Conversant Solutions. All rights reserved.
 * <p/>
 * Created on 2017/1/8.
 */
public class TraderSpiAdapter implements TraderSpi {

    @Override
    public void onFrontConnected() {
        System.out.println("trader connected.....");
    }

    @Override
    public void onFrontDisconnected(int nReason) {

    }

    @Override
    public void onRspUserLogin(CThostFtdcRspUserLoginField pRspUserLogin, CThostFtdcRspInfoField pRspInfo, int nRequestID, boolean bIsLast) {

    }


}

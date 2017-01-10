package org.zhps.hjctp.spi;

import org.zhps.hjctp.entity.CThostFtdcRspInfoField;
import org.zhps.hjctp.entity.CThostFtdcRspUserLoginField;
import org.zhps.hjctp.entity.CThostFtdcSettlementInfoConfirmField;

/**
 * Copyright (c) 2012 Conversant Solutions. All rights reserved.
 * <p/>
 * Created on 2017/1/8.
 */
public interface TraderSpi {
    void onFrontConnected();

    void onFrontDisconnected(int nReason);

    void onRspUserLogin(CThostFtdcRspUserLoginField pRspUserLogin, CThostFtdcRspInfoField pRspInfo, int nRequestID, boolean bIsLast);

    void onRspSettlementInfoConfirm(CThostFtdcSettlementInfoConfirmField pSettlementInfoConfirm, CThostFtdcRspInfoField pRspInfo, int nRequestID, boolean bIsLast);
}

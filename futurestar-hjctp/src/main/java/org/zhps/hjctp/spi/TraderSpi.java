package org.zhps.hjctp.spi;

import org.zhps.hjctp.entity.*;

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

    void onRspQryTradingAccount(CThostFtdcTradingAccountField pTradingAccount, CThostFtdcRspInfoField pRspInfo, int nRequestID, boolean bIsLast);

    void onRspQryInvestorPosition(CThostFtdcInvestorPositionField pInvestorPosition, CThostFtdcRspInfoField pRspInfo, int nRequestID, boolean bIsLast);

    void onRspQryInvestorPositionDetail(CThostFtdcInvestorPositionDetailField pInvestorPositionDetail, CThostFtdcRspInfoField pRspInfo, int nRequestID, boolean bIsLast);

    void onRtnOrder(CThostFtdcOrderField pOrder);

    void onRspOrderInsert(CThostFtdcInputOrderField pInputOrder, CThostFtdcRspInfoField pRspInfo, int nRequestID, boolean bIsLast);

    void onErrRtnOrderInsert(CThostFtdcInputOrderField pInputOrder, CThostFtdcRspInfoField pRspInfo);

    void onRtnTrade(CThostFtdcTradeField pTrade);

    void onRspOrderAction(CThostFtdcInputOrderActionField pInputOrderAction, CThostFtdcRspInfoField pRspInfo, int nRequestID, boolean bIsLast);

    void onErrRtnOrderAction(CThostFtdcOrderActionField pOrderAction, CThostFtdcRspInfoField pRspInfo);

    void onRspQryOrder(CThostFtdcOrderField pOrder, CThostFtdcRspInfoField pRspInfo, int nRequestID, boolean bIsLast);
}

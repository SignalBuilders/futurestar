package org.zhps.trader.spi;

import org.zhps.hjctp.entity.*;
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
        System.out.println(nReason);
    }

    @Override
    public void onRspUserLogin(CThostFtdcRspUserLoginField pRspUserLogin, CThostFtdcRspInfoField pRspInfo, int nRequestID, boolean bIsLast) {
        StringBuilder loginInfo = new StringBuilder("Login Success: ").append(pRspUserLogin.getTradingDay());
        System.out.println(loginInfo.toString());
        System.out.println(pRspInfo.toString());
    }

    @Override
    public void onRspSettlementInfoConfirm(CThostFtdcSettlementInfoConfirmField pSettlementInfoConfirm, CThostFtdcRspInfoField pRspInfo, int nRequestID, boolean bIsLast) {
        StringBuilder settlementInfo = new StringBuilder("Settlement Success: ").append(pSettlementInfoConfirm.getConfirmDate())
                .append(" ").append(pSettlementInfoConfirm.getConfirmTime());
        System.out.println(settlementInfo);
    }

    @Override
    public void onRspQryTradingAccount(CThostFtdcTradingAccountField pTradingAccount, CThostFtdcRspInfoField pRspInfo, int nRequestID, boolean bIsLast) {
        System.out.println("trading account: " + pTradingAccount);
    }

    @Override
    public void onRspQryInvestorPosition(CThostFtdcInvestorPositionField pInvestorPosition, CThostFtdcRspInfoField pRspInfo, int nRequestID, boolean bIsLast) {
        System.out.println(pInvestorPosition);
    }

    @Override
    public void onRspQryInvestorPositionDetail(CThostFtdcInvestorPositionDetailField pInvestorPositionDetail, CThostFtdcRspInfoField pRspInfo, int nRequestID, boolean bIsLast) {
        System.out.println(pInvestorPositionDetail);
    }

    @Override
    public void onRtnOrder(CThostFtdcOrderField pOrder) {
        System.out.println("onRtnOrder++: " + pOrder);
    }

    @Override
    public void onRspOrderInsert(CThostFtdcInputOrderField pInputOrder, CThostFtdcRspInfoField pRspInfo, int nRequestID, boolean bIsLast) {
        System.out.println("onRspOrderInsert++: " + pInputOrder);
    }

    @Override
    public void onErrRtnOrderInsert(CThostFtdcInputOrderField pInputOrder, CThostFtdcRspInfoField pRspInfo) {
        System.out.println("onErrRtnOrderInsert++: " + pInputOrder);
    }

    @Override
    public void onRtnTrade(CThostFtdcTradeField pTrade) {
        System.out.println("onRtnTrade++: " + pTrade);
    }

    @Override
    public void onRspOrderAction(CThostFtdcInputOrderActionField pInputOrderAction, CThostFtdcRspInfoField pRspInfo, int nRequestID, boolean bIsLast) {
        System.out.println("onRspOrderAction++: " + pInputOrderAction);
    }

    @Override
    public void onErrRtnOrderAction(CThostFtdcOrderActionField pOrderAction, CThostFtdcRspInfoField pRspInfo) {
        System.out.println("onErrRtnOrderAction++: " + pOrderAction);
    }

    @Override
    public void onRspQryOrder(CThostFtdcOrderField pOrder, CThostFtdcRspInfoField pRspInfo, int nRequestID, boolean bIsLast) {
        System.out.println("onRspQryOrder++: " + pOrder);
    }
}

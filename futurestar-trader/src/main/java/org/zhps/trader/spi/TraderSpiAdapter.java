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
//        System.out.println("trader connected.....");
    }

    @Override
    public void onFrontDisconnected(int nReason) {
        System.out.println(nReason);
    }

    @Override
    public void onRspUserLogin(CThostFtdcRspUserLoginField pRspUserLogin, CThostFtdcRspInfoField pRspInfo, int nRequestID, boolean bIsLast) {
        StringBuilder loginInfo = new StringBuilder("Login Success: ").append(pRspUserLogin.getTradingDay());
        System.out.println(loginInfo.toString());
//        System.out.println(pRspInfo.toString());
    }

    @Override
    public void onRspSettlementInfoConfirm(CThostFtdcSettlementInfoConfirmField pSettlementInfoConfirm, CThostFtdcRspInfoField pRspInfo, int nRequestID, boolean bIsLast) {
        StringBuilder settlementInfo = new StringBuilder("Settlement Success: ").append(pSettlementInfoConfirm.getConfirmDate())
                .append(" ").append(pSettlementInfoConfirm.getConfirmTime());
        System.out.println(settlementInfo);
    }

    @Override
    public void onRspQryTradingAccount(CThostFtdcTradingAccountField pTradingAccount, CThostFtdcRspInfoField pRspInfo, int nRequestID, boolean bIsLast) {
//        System.out.println("trading account: " + pTradingAccount);
        StringBuilder rtn = new StringBuilder("#onRspQryTradingAccount#\n")
                .append("tradingDay:").append(pTradingAccount.getTradingDay())
                .append("|positionProfit:").append(pTradingAccount.getPositionProfit())
                .append("|available:").append(pTradingAccount.getAvailable())
                .append("|withdrawQuota:").append(pTradingAccount.getWithdrawQuota())
                .append("|commission:").append(pTradingAccount.getCommission())
                .append("|deposit:").append(pTradingAccount.getDeposit())
                .append("|withdraw:").append(pTradingAccount.getWithdraw())
                .append("|frozenMargin:").append(pTradingAccount.getFrozenMargin())
                .append("|currMargin:").append(pTradingAccount.getCurrMargin())
                .append("|exchangeMargin:").append(pTradingAccount.getExchangeMargin())
                .append("|preMargin:").append(pTradingAccount.getPreMargin());
        System.out.println(rtn);
    }

    @Override
    public void onRspQryInvestorPosition(CThostFtdcInvestorPositionField pInvestorPosition, CThostFtdcRspInfoField pRspInfo, int nRequestID, boolean bIsLast) {
//        System.out.println(pInvestorPosition);
        if(pInvestorPosition != null){
            StringBuilder rtn = new StringBuilder("#onRspQryInvestorPosition#\n")
                    .append("tradingDay:").append(pInvestorPosition.getTradingDay())
                    .append("|instrumentID:").append(pInvestorPosition.getInstrumentID())
                    .append("|posiDirection:").append(pInvestorPosition.getPosiDirection())
                    .append("|positionDate:").append(pInvestorPosition.getPositionDate())
                    .append("|ydPosition:").append(pInvestorPosition.getYdPosition())
                    .append("|position:").append(pInvestorPosition.getPosition())
                    .append("|openVolume:").append(pInvestorPosition.getOpenVolume())
                    .append("|closeVolume:").append(pInvestorPosition.getCloseVolume())
                    .append("|useMargin:").append(pInvestorPosition.getUseMargin())
                    .append("|commission:").append(pInvestorPosition.getCommission())
                    .append("|positionProfit:").append(pInvestorPosition.getPositionProfit())
                    .append("|openCost:").append(pInvestorPosition.getOpenCost())
                    .append("|closeProfitByTrade:").append(pInvestorPosition.getCloseProfitByTrade())
                    .append("|todayPosition:").append(pInvestorPosition.getTodayPosition());
            System.out.println(rtn);
        }else{
            System.out.println("onRspQryInvestorPosition no message");
        }
    }

    @Override
    public void onRspQryInvestorPositionDetail(CThostFtdcInvestorPositionDetailField pInvestorPositionDetail, CThostFtdcRspInfoField pRspInfo, int nRequestID, boolean bIsLast) {
//        System.out.println(pInvestorPositionDetail);
        if(pInvestorPositionDetail != null){
            StringBuilder rtn = new StringBuilder("#onRspQryInvestorPositionDetail#\n")
                    .append("tradingDay:").append(pInvestorPositionDetail.getTradingDay())
                    .append("|instrumentID:").append(pInvestorPositionDetail.getInstrumentID())
                    .append("|openDate:").append(pInvestorPositionDetail.getOpenDate())
                    .append("|direction:").append(pInvestorPositionDetail.getDirection())
                    .append("|openPrice:").append(pInvestorPositionDetail.getOpenPrice())
                    .append("|volume:").append(pInvestorPositionDetail.getVolume())
                    .append("|closeProfitByTrade:").append(pInvestorPositionDetail.getCloseProfitByTrade())
                    .append("|margin:").append(pInvestorPositionDetail.getMargin())
                    .append("|exchMargin:").append(pInvestorPositionDetail.getExchMargin())
                    .append("|closeVolume:").append(pInvestorPositionDetail.getCloseVolume())
                    .append("|closeAmount:").append(pInvestorPositionDetail.getCloseAmount());
            System.out.println(rtn);
        }else{
            System.out.println("onRspQryInvestorPositionDetail no message");
        }
    }

    @Override
    public void onRtnOrder(CThostFtdcOrderField pOrder) {
//        System.out.println("onRtnOrder++: " + pOrder);
        if(pOrder != null){
            StringBuilder rtn = new StringBuilder("#onRtnOrder#\n")
                    .append("tradingDay:").append(pOrder.getTradingDay())
                    .append("|instrumentID:").append(pOrder.getInstrumentID())
                    .append("|insertDate:").append(pOrder.getInsertDate())
                    .append("|insertTime:").append(pOrder.getInsertTime())
                    .append("|limitPrice:").append(pOrder.getLimitPrice())
                    .append("|direction:").append(pOrder.getDirection())
                    .append("|combOffsetFlag:").append(pOrder.getCombOffsetFlag())
                    .append("|volumeTotalOriginal:").append(pOrder.getVolumeTotalOriginal())
                    .append("|volumeTraded:").append(pOrder.getVolumeTraded())
                    .append("|volumeTotal:").append(pOrder.getVolumeTotal())
                    .append("|orderPriceType:").append(pOrder.getOrderPriceType())
                    .append("|timeCondition:").append(pOrder.getTimeCondition())
                    .append("|volumeCondition:").append(pOrder.getVolumeCondition())
                    .append("|contingentCondition:").append(pOrder.getContingentCondition())
                    .append("|exchangeID:").append(pOrder.getExchangeID())
                    .append("|orderSysID:").append(pOrder.getOrderSysID())
                    .append("|orderStatus:").append(pOrder.getOrderStatus())
                    .append("|orderType:").append(pOrder.getOrderType())
                    .append("|statusMsg:").append(pOrder.getStatusMsg());
            System.out.println(rtn);
        }else{
            System.out.println("onRtnOrder no message");
        }
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
//        System.out.println("onRtnTrade++: " + pTrade);
        StringBuilder rtn = new StringBuilder("#onRtnTrade#/n")
                .append("tradeDate:").append(pTrade.getTradeDate())
                .append("tradeTime:").append(pTrade.getTradeTime())
                .append("instrumentID:").append(pTrade.getInstrumentID())
                .append("direction:").append(pTrade.getDirection())
                .append("offsetFlag:").append(pTrade.getOffsetFlag())
                .append("exchangeID:").append(pTrade.getExchangeID())
                .append("orderSysID:").append(pTrade.getOrderSysID())
                .append("price:").append(pTrade.getPrice())
                .append("volume:").append(pTrade.getVolume());
        System.out.println(rtn);
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

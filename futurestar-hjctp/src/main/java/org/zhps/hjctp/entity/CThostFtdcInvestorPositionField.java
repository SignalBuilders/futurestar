package org.zhps.hjctp.entity;

import java.io.Serializable;

/**
 * Copyright (c) 2012 Conversant Solutions. All rights reserved.
 * <p/>
 * Created on 2017/1/11.
 */
public class CThostFtdcInvestorPositionField implements Serializable {
    private String instrumentID;

    private String brokerID;

    private String investorID;

    private String posiDirection;

    private String hedgeFlag;

    private String positionDate;

    private int ydPosition;

    private int position;

    private int longFrozen;

    private int shortFrozen;

    private double longFrozenAmount;

    private double shortFrozenAmount;

    private int openVolume;

    private int closeVolume;

    private double openAmount;

    private double closeAmount;

    private double positionCost;

    private double preMargin;

    private double useMargin;

    private double frozenMargin;

    private double frozenCash;

    private double frozenCommission;

    private double commission;

    private double closeProfit;

    private double positionProfit;

    private double preSettlementPrice;

    private double settlementPrice;

    private String tradingDay;

    private int settlementID;

    private double openCost;

    private double exchangeMargin;

    private double closeProfitByDate;

    private double closeProfitByTrade;

    private int todayPosition;

    public String getInstrumentID() {
        return instrumentID;
    }

    public void setInstrumentID(String instrumentID) {
        this.instrumentID = instrumentID;
    }

    public String getBrokerID() {
        return brokerID;
    }

    public void setBrokerID(String brokerID) {
        this.brokerID = brokerID;
    }

    public String getInvestorID() {
        return investorID;
    }

    public void setInvestorID(String investorID) {
        this.investorID = investorID;
    }

    public String getPosiDirection() {
        return posiDirection;
    }

    public void setPosiDirection(String posiDirection) {
        this.posiDirection = posiDirection;
    }

    public String getHedgeFlag() {
        return hedgeFlag;
    }

    public void setHedgeFlag(String hedgeFlag) {
        this.hedgeFlag = hedgeFlag;
    }

    public String getPositionDate() {
        return positionDate;
    }

    public void setPositionDate(String positionDate) {
        this.positionDate = positionDate;
    }

    public int getYdPosition() {
        return ydPosition;
    }

    public void setYdPosition(int ydPosition) {
        this.ydPosition = ydPosition;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getLongFrozen() {
        return longFrozen;
    }

    public void setLongFrozen(int longFrozen) {
        this.longFrozen = longFrozen;
    }

    public int getShortFrozen() {
        return shortFrozen;
    }

    public void setShortFrozen(int shortFrozen) {
        this.shortFrozen = shortFrozen;
    }

    public double getLongFrozenAmount() {
        return longFrozenAmount;
    }

    public void setLongFrozenAmount(double longFrozenAmount) {
        this.longFrozenAmount = longFrozenAmount;
    }

    public double getShortFrozenAmount() {
        return shortFrozenAmount;
    }

    public void setShortFrozenAmount(double shortFrozenAmount) {
        this.shortFrozenAmount = shortFrozenAmount;
    }

    public int getOpenVolume() {
        return openVolume;
    }

    public void setOpenVolume(int openVolume) {
        this.openVolume = openVolume;
    }

    public int getCloseVolume() {
        return closeVolume;
    }

    public void setCloseVolume(int closeVolume) {
        this.closeVolume = closeVolume;
    }

    public double getOpenAmount() {
        return openAmount;
    }

    public void setOpenAmount(double openAmount) {
        this.openAmount = openAmount;
    }

    public double getCloseAmount() {
        return closeAmount;
    }

    public void setCloseAmount(double closeAmount) {
        this.closeAmount = closeAmount;
    }

    public double getPositionCost() {
        return positionCost;
    }

    public void setPositionCost(double positionCost) {
        this.positionCost = positionCost;
    }

    public double getPreMargin() {
        return preMargin;
    }

    public void setPreMargin(double preMargin) {
        this.preMargin = preMargin;
    }

    public double getUseMargin() {
        return useMargin;
    }

    public void setUseMargin(double useMargin) {
        this.useMargin = useMargin;
    }

    public double getFrozenMargin() {
        return frozenMargin;
    }

    public void setFrozenMargin(double frozenMargin) {
        this.frozenMargin = frozenMargin;
    }

    public double getFrozenCash() {
        return frozenCash;
    }

    public void setFrozenCash(double frozenCash) {
        this.frozenCash = frozenCash;
    }

    public double getFrozenCommission() {
        return frozenCommission;
    }

    public void setFrozenCommission(double frozenCommission) {
        this.frozenCommission = frozenCommission;
    }

    public double getCommission() {
        return commission;
    }

    public void setCommission(double commission) {
        this.commission = commission;
    }

    public double getCloseProfit() {
        return closeProfit;
    }

    public void setCloseProfit(double closeProfit) {
        this.closeProfit = closeProfit;
    }

    public double getPositionProfit() {
        return positionProfit;
    }

    public void setPositionProfit(double positionProfit) {
        this.positionProfit = positionProfit;
    }

    public double getPreSettlementPrice() {
        return preSettlementPrice;
    }

    public void setPreSettlementPrice(double preSettlementPrice) {
        this.preSettlementPrice = preSettlementPrice;
    }

    public double getSettlementPrice() {
        return settlementPrice;
    }

    public void setSettlementPrice(double settlementPrice) {
        this.settlementPrice = settlementPrice;
    }

    public String getTradingDay() {
        return tradingDay;
    }

    public void setTradingDay(String tradingDay) {
        this.tradingDay = tradingDay;
    }

    public int getSettlementID() {
        return settlementID;
    }

    public void setSettlementID(int settlementID) {
        this.settlementID = settlementID;
    }

    public double getOpenCost() {
        return openCost;
    }

    public void setOpenCost(double openCost) {
        this.openCost = openCost;
    }

    public double getExchangeMargin() {
        return exchangeMargin;
    }

    public void setExchangeMargin(double exchangeMargin) {
        this.exchangeMargin = exchangeMargin;
    }

    public double getCloseProfitByDate() {
        return closeProfitByDate;
    }

    public void setCloseProfitByDate(double closeProfitByDate) {
        this.closeProfitByDate = closeProfitByDate;
    }

    public double getCloseProfitByTrade() {
        return closeProfitByTrade;
    }

    public void setCloseProfitByTrade(double closeProfitByTrade) {
        this.closeProfitByTrade = closeProfitByTrade;
    }

    public int getTodayPosition() {
        return todayPosition;
    }

    public void setTodayPosition(int todayPosition) {
        this.todayPosition = todayPosition;
    }

    @Override
    public String toString() {
        return new StringBuilder("CThostFtdcInvestorPositionField{")
                .append("instrumentID='").append(instrumentID).append("'")
                .append(", brokerID='").append(brokerID).append("'")
                .append(", investorID='").append(investorID).append("'")
                .append(", posiDirection='").append(posiDirection).append("'")
                .append(", hedgeFlag='").append(hedgeFlag).append("'")
                .append(", positionDate='").append(positionDate).append("'")
                .append(", ydPosition=").append(ydPosition)
                .append(", position=").append(position)
                .append(", longFrozen=").append(longFrozen)
                .append(", shortFrozen=").append(shortFrozen)
                .append(", longFrozenAmount=").append(longFrozenAmount)
                .append(", shortFrozenAmount=").append(shortFrozenAmount)
                .append(", openVolume=").append(openVolume)
                .append(", closeVolume=").append(closeVolume)
                .append(", openAmount=").append(openAmount)
                .append(", closeAmount=").append(closeAmount)
                .append(", positionCost=").append(positionCost)
                .append(", preMargin=").append(preMargin)
                .append(", useMargin=").append(useMargin)
                .append(", frozenMargin=").append(frozenMargin)
                .append(", frozenCash=").append(frozenCash)
                .append(", frozenCommission=").append(frozenCommission)
                .append(", commission=").append(commission)
                .append(", closeProfit=").append(closeProfit)
                .append(", positionProfit=").append(positionProfit)
                .append(", preSettlementPrice=").append(preSettlementPrice)
                .append(", settlementPrice=").append(settlementPrice)
                .append(", tradingDay='").append(tradingDay).append("'")
                .append(", settlementID=").append(settlementID)
                .append(", openCost=").append(openCost)
                .append(", exchangeMargin=").append(exchangeMargin)
                .append(", closeProfitByDate=").append(closeProfitByDate)
                .append(", closeProfitByTrade=").append(closeProfitByTrade)
                .append(", todayPosition=").append(todayPosition)
                .append("}").toString();
    }
}

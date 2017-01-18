package org.zhps.hjctp.entity;

import java.io.Serializable;

/**
 * Copyright (c) 2012 Conversant Solutions. All rights reserved.
 * <p/>
 * Created on 2017/1/11.
 */
public class CThostFtdcInvestorPositionDetailField implements Serializable {
    
    private String instrumentID;
    
    private String brokerID;
    
    private String investorID;
    
    private char hedgeFlag;
    
    private char direction;
    
    private String openDate;
    
    private String tradeID;
    
    private int volume;
    
    private double openPrice;
    
    private String tradingDay;
    
    private int settlementID;
    
    private char tradeType;
    
    private String combInstrumentID;
    
    private String exchangeID;
    
    private double closeProfitByDate;
    
    private double closeProfitByTrade;
    
    private double positionProfitByDate;
    
    private double positionProfitByTrade;
    
    private double margin;
    
    private double exchMargin;
    
    private double marginRateByMoney;
    
    private double marginRateByVolume;
    
    private double lastSettlementPrice;
    
    private double settlementPrice;
    
    private int closeVolume;
    
    private double closeAmount;

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

    public String getOpenDate() {
        return openDate;
    }

    public void setOpenDate(String openDate) {
        this.openDate = openDate;
    }

    public String getTradeID() {
        return tradeID;
    }

    public void setTradeID(String tradeID) {
        this.tradeID = tradeID;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public double getOpenPrice() {
        return openPrice;
    }

    public void setOpenPrice(double openPrice) {
        this.openPrice = openPrice;
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

    public String getCombInstrumentID() {
        return combInstrumentID;
    }

    public void setCombInstrumentID(String combInstrumentID) {
        this.combInstrumentID = combInstrumentID;
    }

    public String getExchangeID() {
        return exchangeID;
    }

    public void setExchangeID(String exchangeID) {
        this.exchangeID = exchangeID;
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

    public double getPositionProfitByDate() {
        return positionProfitByDate;
    }

    public void setPositionProfitByDate(double positionProfitByDate) {
        this.positionProfitByDate = positionProfitByDate;
    }

    public double getPositionProfitByTrade() {
        return positionProfitByTrade;
    }

    public void setPositionProfitByTrade(double positionProfitByTrade) {
        this.positionProfitByTrade = positionProfitByTrade;
    }

    public double getMargin() {
        return margin;
    }

    public void setMargin(double margin) {
        this.margin = margin;
    }

    public double getExchMargin() {
        return exchMargin;
    }

    public void setExchMargin(double exchMargin) {
        this.exchMargin = exchMargin;
    }

    public double getMarginRateByMoney() {
        return marginRateByMoney;
    }

    public void setMarginRateByMoney(double marginRateByMoney) {
        this.marginRateByMoney = marginRateByMoney;
    }

    public double getMarginRateByVolume() {
        return marginRateByVolume;
    }

    public void setMarginRateByVolume(double marginRateByVolume) {
        this.marginRateByVolume = marginRateByVolume;
    }

    public double getLastSettlementPrice() {
        return lastSettlementPrice;
    }

    public void setLastSettlementPrice(double lastSettlementPrice) {
        this.lastSettlementPrice = lastSettlementPrice;
    }

    public double getSettlementPrice() {
        return settlementPrice;
    }

    public void setSettlementPrice(double settlementPrice) {
        this.settlementPrice = settlementPrice;
    }

    public int getCloseVolume() {
        return closeVolume;
    }

    public void setCloseVolume(int closeVolume) {
        this.closeVolume = closeVolume;
    }

    public double getCloseAmount() {
        return closeAmount;
    }

    public void setCloseAmount(double closeAmount) {
        this.closeAmount = closeAmount;
    }

    public char getHedgeFlag() {
        return hedgeFlag;
    }

    public void setHedgeFlag(char hedgeFlag) {
        this.hedgeFlag = hedgeFlag;
    }

    public char getDirection() {
        return direction;
    }

    public void setDirection(char direction) {
        this.direction = direction;
    }

    public char getTradeType() {
        return tradeType;
    }

    public void setTradeType(char tradeType) {
        this.tradeType = tradeType;
    }

    @Override
    public String toString() {
        return new StringBuilder("CThostFtdcInvestorPositionDetailField{")
                .append("instrumentID='").append(instrumentID).append("'")
                .append(", brokerID='").append(brokerID).append("'")
                .append(", investorID='").append(investorID).append("'")
                .append(", hedgeFlag=").append(hedgeFlag)
                .append(", direction=").append(direction)
                .append(", openDate='").append(openDate).append("'")
                .append(", tradeID='").append(tradeID).append("'")
                .append(", volume=").append(volume)
                .append(", openPrice=").append(openPrice)
                .append(", tradingDay=").append(tradingDay)
                .append(", settlementID=").append(settlementID)
                .append(", tradeType=").append(tradeType)
                .append(", combInstrumentID='").append(combInstrumentID).append("'")
                .append(", exchangeID='").append(exchangeID).append("'")
                .append(", closeProfitByDate=").append(closeProfitByDate)
                .append(", closeProfitByTrade=").append(closeProfitByTrade)
                .append(", positionProfitByDate=").append(positionProfitByDate)
                .append(", positionProfitByTrade=").append(positionProfitByTrade)
                .append(", margin=").append(margin)
                .append(", exchMargin=").append(exchMargin)
                .append(", marginRateByMoney=").append(marginRateByMoney)
                .append(", marginRateByVolume=").append(marginRateByVolume)
                .append(", lastSettlementPrice=").append(lastSettlementPrice)
                .append(", settlementPrice=").append(settlementPrice)
                .append(", closeVolume=").append(closeVolume)
                .append(", closeAmount=").append(closeAmount)
                .append("}").toString();
    }
}

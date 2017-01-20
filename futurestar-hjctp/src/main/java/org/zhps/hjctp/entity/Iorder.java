package org.zhps.hjctp.entity;

import java.io.Serializable;

/**
 * Copyright (c) 2012 Conversant Solutions. All rights reserved.
 * <p/>
 * Created on 2017/1/10.
 */
public class Iorder implements Serializable {

    private String brokerID;

    private String investorID;

    private String instrumentID;

    private String orderRef;

    private String userID;

    private char orderPriceType;

    private char direction;

    private char combOffsetFlag;

    private char combHedgeFlag;

    private double limitPrice;

    private int volumeTotalOriginal;

    private char timeCondition;

    private String gTDDate;

    private char volumeCondition;

    private int minVolume;

    private char contingentCondition;

    private double stopPrice;

    private char forceCloseReason;

    private int isAutoSuspend;

    private String businessUnit;

    private int requestID;

    private int userForceClose;

    private int isSwapOrder;

    private String exchangeID;

    private String investUnitID;

    private String accountID;

    private String currencyID;

    private String clientID;

    private String iPAddress;

    private String macAddress;

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

    public String getInstrumentID() {
        return instrumentID;
    }

    public void setInstrumentID(String instrumentID) {
        this.instrumentID = instrumentID;
    }

    public String getOrderRef() {
        return orderRef;
    }

    public void setOrderRef(String orderRef) {
        this.orderRef = orderRef;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public char getOrderPriceType() {
        return orderPriceType;
    }

    public void setOrderPriceType(char orderPriceType) {
        this.orderPriceType = orderPriceType;
    }

    public char getDirection() {
        return direction;
    }

    public void setDirection(char direction) {
        this.direction = direction;
    }

    public char getCombOffsetFlag() {
        return combOffsetFlag;
    }

    public void setCombOffsetFlag(char combOffsetFlag) {
        this.combOffsetFlag = combOffsetFlag;
    }

    public char getCombHedgeFlag() {
        return combHedgeFlag;
    }

    public void setCombHedgeFlag(char combHedgeFlag) {
        this.combHedgeFlag = combHedgeFlag;
    }

    public double getLimitPrice() {
        return limitPrice;
    }

    public void setLimitPrice(double limitPrice) {
        this.limitPrice = limitPrice;
    }

    public int getVolumeTotalOriginal() {
        return volumeTotalOriginal;
    }

    public void setVolumeTotalOriginal(int volumeTotalOriginal) {
        this.volumeTotalOriginal = volumeTotalOriginal;
    }

    public char getTimeCondition() {
        return timeCondition;
    }

    public void setTimeCondition(char timeCondition) {
        this.timeCondition = timeCondition;
    }

    public String getgTDDate() {
        return gTDDate;
    }

    public void setgTDDate(String gTDDate) {
        this.gTDDate = gTDDate;
    }

    public char getVolumeCondition() {
        return volumeCondition;
    }

    public void setVolumeCondition(char volumeCondition) {
        this.volumeCondition = volumeCondition;
    }

    public int getMinVolume() {
        return minVolume;
    }

    public void setMinVolume(int minVolume) {
        this.minVolume = minVolume;
    }

    public char getContingentCondition() {
        return contingentCondition;
    }

    public void setContingentCondition(char contingentCondition) {
        this.contingentCondition = contingentCondition;
    }

    public double getStopPrice() {
        return stopPrice;
    }

    public void setStopPrice(double stopPrice) {
        this.stopPrice = stopPrice;
    }

    public char getForceCloseReason() {
        return forceCloseReason;
    }

    public void setForceCloseReason(char forceCloseReason) {
        this.forceCloseReason = forceCloseReason;
    }

    public int getIsAutoSuspend() {
        return isAutoSuspend;
    }

    public void setIsAutoSuspend(int isAutoSuspend) {
        this.isAutoSuspend = isAutoSuspend;
    }

    public String getBusinessUnit() {
        return businessUnit;
    }

    public void setBusinessUnit(String businessUnit) {
        this.businessUnit = businessUnit;
    }

    public int getRequestID() {
        return requestID;
    }

    public void setRequestID(int requestID) {
        this.requestID = requestID;
    }

    public int getUserForceClose() {
        return userForceClose;
    }

    public void setUserForceClose(int userForceClose) {
        this.userForceClose = userForceClose;
    }

    public int getIsSwapOrder() {
        return isSwapOrder;
    }

    public void setIsSwapOrder(int isSwapOrder) {
        this.isSwapOrder = isSwapOrder;
    }

    public String getExchangeID() {
        return exchangeID;
    }

    public void setExchangeID(String exchangeID) {
        this.exchangeID = exchangeID;
    }

    public String getInvestUnitID() {
        return investUnitID;
    }

    public void setInvestUnitID(String investUnitID) {
        this.investUnitID = investUnitID;
    }

    public String getAccountID() {
        return accountID;
    }

    public void setAccountID(String accountID) {
        this.accountID = accountID;
    }

    public String getCurrencyID() {
        return currencyID;
    }

    public void setCurrencyID(String currencyID) {
        this.currencyID = currencyID;
    }

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    public String getiPAddress() {
        return iPAddress;
    }

    public void setiPAddress(String iPAddress) {
        this.iPAddress = iPAddress;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    @Override
    public String toString() {
        return new StringBuilder("Iorder{")
                .append("brokerID='").append(brokerID).append("'")
                .append(", investorID='").append(investorID).append("'")
                .append(", instrumentID='").append(instrumentID).append("'")
                .append(", orderRef='").append(orderRef).append("'")
                .append(", userID='").append(userID).append("'")
                .append(", orderPriceType=").append(orderPriceType)
                .append(", direction=").append(direction)
                .append(", combOffsetFlag=").append(combOffsetFlag)
                .append(", combHedgeFlag=").append(combHedgeFlag)
                .append(", limitPrice=").append(limitPrice)
                .append(", volumeTotalOriginal=").append(volumeTotalOriginal)
                .append(", timeCondition=").append(timeCondition)
                .append(", gTDDate='").append(gTDDate).append("'")
                .append(", volumeCondition=").append(volumeCondition)
                .append(", minVolume=").append(minVolume)
                .append(", contingentCondition=").append(contingentCondition)
                .append(", stopPrice=").append(stopPrice)
                .append(", forceCloseReason=").append(forceCloseReason)
                .append(", isAutoSuspend=").append(isAutoSuspend)
                .append(", businessUnit='").append(businessUnit).append("'")
                .append(", requestID=").append(requestID)
                .append(", userForceClose=").append(userForceClose)
                .append(", isSwapOrder=").append(isSwapOrder)
                .append(", exchangeID='").append(exchangeID).append("'")
                .append(", investUnitID='").append(investUnitID).append("'")
                .append(", accountID='").append(accountID).append("'")
                .append(", currencyID='").append(currencyID).append("'")
                .append(", clientID='").append(clientID).append("'")
                .append(", iPAddress='").append(iPAddress).append("'")
                .append(", macAddress='").append(macAddress).append("'")
                .append("}").toString();
    }
}

package org.zhps.hjctp.entity;

import java.io.Serializable;

/**
 * Copyright (c) 2012 Conversant Solutions. All rights reserved.
 * <p/>
 * Created on 2017/1/11.
 */
public class CThostFtdcInputOrderField implements Serializable {
    
    private String brokerID;
    
    private String investorID;
    
    private String instrumentID;
    
    private String orderRef;
    
    private String userID;
    
    private String orderPriceType;
    
    private String direction;
    
    private String combOffsetFlag;
    
    private String combHedgeFlag;
    
    private double limitPrice;
    
    private int volumeTotalOriginal;
    
    private String timeCondition;
    
    private String gTDDate;
    
    private int volumeCondition;
    
    private int minVolume;
    
    private String contingentCondition;
    
    private double stopPrice;
    
    private String forceCloseReason;
    
    private int isAutoSuspend;
    
    private String businessUnit;
    
    private int requestID;
    
    private int userForceClose;
    
    private int isSwapOrder;
    
    private String exchangeID;
    
    private String investUnitID;
    
    private String accountID;
    
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

    public String getOrderPriceType() {
        return orderPriceType;
    }

    public void setOrderPriceType(String orderPriceType) {
        this.orderPriceType = orderPriceType;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getCombOffsetFlag() {
        return combOffsetFlag;
    }

    public void setCombOffsetFlag(String combOffsetFlag) {
        this.combOffsetFlag = combOffsetFlag;
    }

    public String getCombHedgeFlag() {
        return combHedgeFlag;
    }

    public void setCombHedgeFlag(String combHedgeFlag) {
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

    public String getTimeCondition() {
        return timeCondition;
    }

    public void setTimeCondition(String timeCondition) {
        this.timeCondition = timeCondition;
    }

    public String getgTDDate() {
        return gTDDate;
    }

    public void setgTDDate(String gTDDate) {
        this.gTDDate = gTDDate;
    }

    public int getVolumeCondition() {
        return volumeCondition;
    }

    public void setVolumeCondition(int volumeCondition) {
        this.volumeCondition = volumeCondition;
    }

    public int getMinVolume() {
        return minVolume;
    }

    public void setMinVolume(int minVolume) {
        this.minVolume = minVolume;
    }

    public String getContingentCondition() {
        return contingentCondition;
    }

    public void setContingentCondition(String contingentCondition) {
        this.contingentCondition = contingentCondition;
    }

    public double getStopPrice() {
        return stopPrice;
    }

    public void setStopPrice(double stopPrice) {
        this.stopPrice = stopPrice;
    }

    public String getForceCloseReason() {
        return forceCloseReason;
    }

    public void setForceCloseReason(String forceCloseReason) {
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
        return new StringBuilder("CThostFtdcInputOrderField{")
                .append("brokerID='").append(brokerID).append("'")
                .append(", investorID='").append(investorID).append("'")
                .append(", instrumentID='").append(instrumentID).append("'")
                .append(", orderRef='").append(orderRef).append("'")
                .append(", userID='").append(userID).append("'")
                .append(", orderPriceType='").append(orderPriceType).append("'")
                .append(", direction='").append(direction).append("'")
                .append(", combOffsetFlag='").append(combOffsetFlag).append("'")
                .append(", combHedgeFlag='").append(combHedgeFlag).append("'")
                .append(", limitPrice=").append(limitPrice)
                .append(", volumeTotalOriginal=").append(volumeTotalOriginal)
                .append(", timeCondition='").append(timeCondition).append("'")
                .append(", gTDDate='").append(gTDDate).append("'")
                .append(", volumeCondition=").append(volumeCondition)
                .append(", minVolume=").append(minVolume)
                .append(", contingentCondition='").append(contingentCondition).append("'")
                .append(", stopPrice=").append(stopPrice)
                .append(", forceCloseReason='").append(forceCloseReason).append("'")
                .append(", isAutoSuspend=").append(isAutoSuspend)
                .append(", businessUnit='").append(businessUnit).append("'")
                .append(", requestID=").append(requestID)
                .append(", userForceClose=").append(userForceClose)
                .append(", isSwapOrder=").append(isSwapOrder)
                .append(", exchangeID='").append(exchangeID).append("'")
                .append(", investUnitID='").append(investUnitID).append("'")
                .append(", accountID='").append(accountID).append("'")
                .append(", clientID='").append(clientID).append("'")
                .append(", iPAddress='").append(iPAddress).append("'")
                .append(", macAddress='").append(macAddress).append("'")
                .append("}").toString();
    }
}

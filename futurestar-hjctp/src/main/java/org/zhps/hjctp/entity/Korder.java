package org.zhps.hjctp.entity;

import java.io.Serializable;

/**
 * Copyright (c) 2012 Conversant Solutions. All rights reserved.
 * <p/>
 * Created on 2017/1/10.
 */
public class Korder implements Serializable {
    //1.FrontID + SessionID + OrderRef
    //2.ExchangeID + TraderID + OrderLocalID
    //3.ExchangeID + OrderSysID
    private String brokerID;
    
    private String investorID;
    
    private int orderActionRef;
    
    private String orderRef;
    
    private int requestID;
    
    private int frontID;
    
    private int sessionID;
    
    private String exchangeID;
    
    private String orderSysID;
    
    private String actionFlag;
    
    private double limitPrice;
    
    private int volumeChange;
    
    private String userID;
    
    private String instrumentID;
    
    private String investUnitID;
    
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

    public int getOrderActionRef() {
        return orderActionRef;
    }

    public void setOrderActionRef(int orderActionRef) {
        this.orderActionRef = orderActionRef;
    }

    public String getOrderRef() {
        return orderRef;
    }

    public void setOrderRef(String orderRef) {
        this.orderRef = orderRef;
    }

    public int getRequestID() {
        return requestID;
    }

    public void setRequestID(int requestID) {
        this.requestID = requestID;
    }

    public int getFrontID() {
        return frontID;
    }

    public void setFrontID(int frontID) {
        this.frontID = frontID;
    }

    public int getSessionID() {
        return sessionID;
    }

    public void setSessionID(int sessionID) {
        this.sessionID = sessionID;
    }

    public String getExchangeID() {
        return exchangeID;
    }

    public void setExchangeID(String exchangeID) {
        this.exchangeID = exchangeID;
    }

    public String getOrderSysID() {
        return orderSysID;
    }

    public void setOrderSysID(String orderSysID) {
        this.orderSysID = orderSysID;
    }

    public String getActionFlag() {
        return actionFlag;
    }

    public void setActionFlag(String actionFlag) {
        this.actionFlag = actionFlag;
    }

    public double getLimitPrice() {
        return limitPrice;
    }

    public void setLimitPrice(double limitPrice) {
        this.limitPrice = limitPrice;
    }

    public int getVolumeChange() {
        return volumeChange;
    }

    public void setVolumeChange(int volumeChange) {
        this.volumeChange = volumeChange;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getInstrumentID() {
        return instrumentID;
    }

    public void setInstrumentID(String instrumentID) {
        this.instrumentID = instrumentID;
    }

    public String getInvestUnitID() {
        return investUnitID;
    }

    public void setInvestUnitID(String investUnitID) {
        this.investUnitID = investUnitID;
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
        return new StringBuilder("Korder{")
                .append("brokerID='").append(brokerID).append("'")
                .append(", investorID='").append(investorID).append("'")
                .append(", orderActionRef=").append(orderActionRef)
                .append(", orderRef='").append(orderRef).append("'")
                .append(", requestID=").append(requestID)
                .append(", frontID=").append(frontID)
                .append(", sessionID=").append(sessionID)
                .append(", exchangeID='").append(exchangeID).append("'")
                .append(", orderSysID='").append(orderSysID).append("'")
                .append(", actionFlag='").append(actionFlag).append("'")
                .append(", limitPrice=").append(limitPrice)
                .append(", volumeChange=").append(volumeChange)
                .append(", userID='").append(userID).append("'")
                .append(", instrumentID='").append(instrumentID).append("'")
                .append(", investUnitID='").append(investUnitID).append("'")
                .append(", iPAddress='").append(iPAddress).append("'")
                .append(", macAddress='").append(macAddress).append("'")
                .append("}").toString();
    }
}

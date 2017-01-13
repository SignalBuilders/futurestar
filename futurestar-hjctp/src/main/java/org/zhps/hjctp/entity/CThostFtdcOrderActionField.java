package org.zhps.hjctp.entity;

import java.io.Serializable;

/**
 * Copyright (c) 2012 Conversant Solutions. All rights reserved.
 * <p/>
 * Created on 2017/1/13.
 */
public class CThostFtdcOrderActionField implements Serializable {
    
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
    
    private String actionDate;
    
    private String actionTime;
    
    private String traderID;
    
    private int installID;
    
    private String orderLocalID;
    
    private String actionLocalID;
    
    private String participantID;
    
    private String clientID;
    
    private String businessUnit;
    
    private String orderActionStatus;
    
    private String userID;
    
    private String statusMsg;
    
    private String instrumentID;
    
    private String branchID;
    
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

    public String getActionDate() {
        return actionDate;
    }

    public void setActionDate(String actionDate) {
        this.actionDate = actionDate;
    }

    public String getActionTime() {
        return actionTime;
    }

    public void setActionTime(String actionTime) {
        this.actionTime = actionTime;
    }

    public String getTraderID() {
        return traderID;
    }

    public void setTraderID(String traderID) {
        this.traderID = traderID;
    }

    public int getInstallID() {
        return installID;
    }

    public void setInstallID(int installID) {
        this.installID = installID;
    }

    public String getOrderLocalID() {
        return orderLocalID;
    }

    public void setOrderLocalID(String orderLocalID) {
        this.orderLocalID = orderLocalID;
    }

    public String getActionLocalID() {
        return actionLocalID;
    }

    public void setActionLocalID(String actionLocalID) {
        this.actionLocalID = actionLocalID;
    }

    public String getParticipantID() {
        return participantID;
    }

    public void setParticipantID(String participantID) {
        this.participantID = participantID;
    }

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    public String getBusinessUnit() {
        return businessUnit;
    }

    public void setBusinessUnit(String businessUnit) {
        this.businessUnit = businessUnit;
    }

    public String getOrderActionStatus() {
        return orderActionStatus;
    }

    public void setOrderActionStatus(String orderActionStatus) {
        this.orderActionStatus = orderActionStatus;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getStatusMsg() {
        return statusMsg;
    }

    public void setStatusMsg(String statusMsg) {
        this.statusMsg = statusMsg;
    }

    public String getInstrumentID() {
        return instrumentID;
    }

    public void setInstrumentID(String instrumentID) {
        this.instrumentID = instrumentID;
    }

    public String getBranchID() {
        return branchID;
    }

    public void setBranchID(String branchID) {
        this.branchID = branchID;
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
        return new StringBuilder("CThostFtdcOrderActionField{")
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
                .append(", actionDate='").append(actionDate).append("'")
                .append(", actionTime='").append(actionTime).append("'")
                .append(", traderID='").append(traderID).append("'")
                .append(", installID=").append(installID)
                .append(", orderLocalID='").append(orderLocalID).append("'")
                .append(", actionLocalID='").append(actionLocalID).append("'")
                .append(", participantID='").append(participantID).append("'")
                .append(", clientID='").append(clientID).append("'")
                .append(", businessUnit='").append(businessUnit).append("'")
                .append(", orderActionStatus='").append(orderActionStatus).append("'")
                .append(", userID='").append(userID).append("'")
                .append(", statusMsg='").append(statusMsg).append("'")
                .append(", instrumentID='").append(instrumentID).append("'")
                .append(", branchID='").append(branchID).append("'")
                .append(", investUnitID='").append(investUnitID).append("'")
                .append(", iPAddress='").append(iPAddress).append("'")
                .append(", macAddress='").append(macAddress).append("'")
                .append("}").toString();
    }
}

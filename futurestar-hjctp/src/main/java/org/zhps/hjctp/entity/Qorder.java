package org.zhps.hjctp.entity;

import java.io.Serializable;

/**
 * Copyright (c) 2012 Conversant Solutions. All rights reserved.
 * <p/>
 * Created on 2017/1/11.
 */
public class Qorder implements Serializable {
    
    private String brokerID;
    
    private String investorID;
    
    private String instrumentID;
    
    private String exchangeID;
    
    private String orderSysID;
    
    private String insertTimeStart;
    
    private String insertTimeEnd;

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

    public String getInsertTimeStart() {
        return insertTimeStart;
    }

    public void setInsertTimeStart(String insertTimeStart) {
        this.insertTimeStart = insertTimeStart;
    }

    public String getInsertTimeEnd() {
        return insertTimeEnd;
    }

    public void setInsertTimeEnd(String insertTimeEnd) {
        this.insertTimeEnd = insertTimeEnd;
    }

    @Override
    public String toString() {
        return new StringBuilder("Qorder{")
                .append("brokerID='").append(brokerID).append("'")
                .append(", investorID='").append(investorID).append("'")
                .append(", instrumentID='").append(instrumentID).append("'")
                .append(", exchangeID='").append(exchangeID).append("'")
                .append(", orderSysID='").append(orderSysID).append("'")
                .append(", insertTimeStart='").append(insertTimeStart).append("'")
                .append(", insertTimeEnd='").append(insertTimeEnd).append("'")
                .append("}").toString();
    }
}

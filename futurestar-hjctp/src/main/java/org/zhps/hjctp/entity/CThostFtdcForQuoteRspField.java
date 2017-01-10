package org.zhps.hjctp.entity;

import java.io.Serializable;

/**
 * Copyright (c) 2012 Conversant Solutions. All rights reserved.
 * <p>
 * Created on 2016/12/11.
 */
public class CThostFtdcForQuoteRspField implements Serializable {

    String tradingDay;

    String instrumentID;

    String forQuoteSysID;

    String forQuoteTime;

    String actionDay;

    String exchangeID;

    public String getTradingDay() {
        return tradingDay;
    }

    public void setTradingDay(String tradingDay) {
        this.tradingDay = tradingDay;
    }

    public String getInstrumentID() {
        return instrumentID;
    }

    public void setInstrumentID(String instrumentID) {
        this.instrumentID = instrumentID;
    }

    public String getForQuoteSysID() {
        return forQuoteSysID;
    }

    public void setForQuoteSysID(String forQuoteSysID) {
        this.forQuoteSysID = forQuoteSysID;
    }

    public String getForQuoteTime() {
        return forQuoteTime;
    }

    public void setForQuoteTime(String forQuoteTime) {
        this.forQuoteTime = forQuoteTime;
    }

    public String getActionDay() {
        return actionDay;
    }

    public void setActionDay(String actionDay) {
        this.actionDay = actionDay;
    }

    public String getExchangeID() {
        return exchangeID;
    }

    public void setExchangeID(String exchangeID) {
        this.exchangeID = exchangeID;
    }

    @Override
    public String toString() {
        return new StringBuilder("CThostFtdcForQuoteRspField{")
                .append("tradingDay='").append(tradingDay).append("'")
                .append(", instrumentID='").append(instrumentID).append("'")
                .append(", forQuoteSysID='").append(forQuoteSysID).append("'")
                .append(", forQuoteTime='").append(forQuoteTime).append("'")
                .append(", actionDay='").append(actionDay).append("'")
                .append(", exchangeID='").append(exchangeID).append("'")
                .append("}").toString();
    }
}

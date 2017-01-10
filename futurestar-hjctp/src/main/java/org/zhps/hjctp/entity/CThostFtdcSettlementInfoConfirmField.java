package org.zhps.hjctp.entity;

import java.io.Serializable;

/**
 * Copyright (c) 2012 Conversant Solutions. All rights reserved.
 * <p/>
 * Created on 2017/1/10.
 */
public class CThostFtdcSettlementInfoConfirmField implements Serializable {
    String brokerID;

    String investorID;

    String confirmDate;

    String confirmTime;

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

    public String getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(String confirmDate) {
        this.confirmDate = confirmDate;
    }

    public String getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(String confirmTime) {
        this.confirmTime = confirmTime;
    }

    @Override
    public String toString() {
        return "CThostFtdcSettlementInfoConfirmField{" +
                "brokerID='" + brokerID + '\'' +
                ", investorID='" + investorID + '\'' +
                ", confirmDate='" + confirmDate + '\'' +
                ", confirmTime='" + confirmTime + '\'' +
                '}';
    }
}

package org.zhps.hjctp.entity;

import java.io.Serializable;

/**
 * Copyright (c) 2012 Conversant Solutions. All rights reserved.
 * <p/>
 * Created on 2017/1/10.
 */
public class CThostFtdcTradingAccountField implements Serializable {

    private String brokerID;

    private String accountID;

    private double preCredit;

    private double preDeposit;

    private double preBalance;

    private double preMargin;

    private double deposit;

    private double withdraw;

    private double frozenMargin;

    private double frozenCash;

    private double frozenCommission;

    private double currMargin;

    private double cashIn;

    private double commission;

    private double closeProfit;

    private double positionProfit;

    private double balance;

    private double available;

    private double withdrawQuota;

    private double reserve;

    private String tradingDay;

    private int settlementID;

    private double credit;

    private double exchangeMargin;

    public String getBrokerID() {
        return brokerID;
    }

    public void setBrokerID(String brokerID) {
        this.brokerID = brokerID;
    }

    public String getAccountID() {
        return accountID;
    }

    public void setAccountID(String accountID) {
        this.accountID = accountID;
    }

    public double getPreCredit() {
        return preCredit;
    }

    public void setPreCredit(double preCredit) {
        this.preCredit = preCredit;
    }

    public double getPreDeposit() {
        return preDeposit;
    }

    public void setPreDeposit(double preDeposit) {
        this.preDeposit = preDeposit;
    }

    public double getPreBalance() {
        return preBalance;
    }

    public void setPreBalance(double preBalance) {
        this.preBalance = preBalance;
    }

    public double getPreMargin() {
        return preMargin;
    }

    public void setPreMargin(double preMargin) {
        this.preMargin = preMargin;
    }

    public double getDeposit() {
        return deposit;
    }

    public void setDeposit(double deposit) {
        this.deposit = deposit;
    }

    public double getWithdraw() {
        return withdraw;
    }

    public void setWithdraw(double withdraw) {
        this.withdraw = withdraw;
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

    public double getCurrMargin() {
        return currMargin;
    }

    public void setCurrMargin(double currMargin) {
        this.currMargin = currMargin;
    }

    public double getCashIn() {
        return cashIn;
    }

    public void setCashIn(double cashIn) {
        this.cashIn = cashIn;
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

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getAvailable() {
        return available;
    }

    public void setAvailable(double available) {
        this.available = available;
    }

    public double getWithdrawQuota() {
        return withdrawQuota;
    }

    public void setWithdrawQuota(double withdrawQuota) {
        this.withdrawQuota = withdrawQuota;
    }

    public double getReserve() {
        return reserve;
    }

    public void setReserve(double reserve) {
        this.reserve = reserve;
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

    public double getCredit() {
        return credit;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }

    public double getExchangeMargin() {
        return exchangeMargin;
    }

    public void setExchangeMargin(double exchangeMargin) {
        this.exchangeMargin = exchangeMargin;
    }

    @Override
    public String toString() {
        return new StringBuilder("CThostFtdcTradingAccountField{")
                .append("brokerID='").append(brokerID).append("'")
                .append(", accountID='").append(accountID).append("'")
                .append(", preCredit=").append(preCredit)
                .append(", preDeposit=").append(preDeposit)
                .append(", preBalance=").append(preBalance)
                .append(", preMargin=").append(preMargin)
                .append(", deposit=").append(deposit)
                .append(", withdraw=").append(withdraw)
                .append(", frozenMargin=").append(frozenMargin)
                .append(", frozenCash=").append(frozenCash)
                .append(", frozenCommission=").append(frozenCommission)
                .append(", currMargin=").append(currMargin)
                .append(", cashIn=").append(cashIn)
                .append(", commission=").append(commission)
                .append(", closeProfit=").append(closeProfit)
                .append(", positionProfit=").append(positionProfit)
                .append(", balance=").append(balance)
                .append(", available=").append(available)
                .append(", withdrawQuota=").append(withdrawQuota)
                .append(", reserve=").append(reserve)
                .append(", tradingDay='").append(tradingDay).append("'")
                .append(", settlementID=").append(settlementID)
                .append(", credit=").append(credit)
                .append(", exchangeMargin=").append(exchangeMargin)
                .append("}").toString();
    }
}

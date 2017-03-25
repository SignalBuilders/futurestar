package org.zhps.strategy.vo;

/**
 * Copyright (c) 2012 Conversant Solutions. All rights reserved.
 * <p>
 * Created on 2017/2/18.
 */
public class Ave5d10dResVO {
    private String tradingDay;

    private int posDirection;

    private int operationType;

    private double price;

    public String getTradingDay() {
        return tradingDay;
    }

    public void setTradingDay(String tradingDay) {
        this.tradingDay = tradingDay;
    }

    public int getPosDirection() {
        return posDirection;
    }

    public void setPosDirection(int posDirection) {
        this.posDirection = posDirection;
    }

    public int getOperationType() {
        return operationType;
    }

    public void setOperationType(int operationType) {
        this.operationType = operationType;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return new StringBuilder("Ave5d10dResVO{")
                .append("tradingDay='").append(tradingDay).append("'")
                .append(", posDirection=").append(posDirection)
                .append(", operationType=").append(operationType)
                .append(", price=").append(price)
                .append("}").toString();
    }
}

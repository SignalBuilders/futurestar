package org.zhps.df.entity;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Copyright (c) 2012 Conversant Solutions. All rights reserved.
 * <p/>
 * Created on 2016/12/26.
 */
public class Quotation implements Serializable {
    //rb1705 2922.0 2971.0 3222.0 2801.0 15:24:39 20161226
    private final static Pattern pattern = Pattern.compile("^(\\D+)(\\d+)");

    private String instrumentId;

    private double lastPrice;

    private double openPrice;

    private double highestPrice;

    private double lowestPrice;

    private double upperLimitPrice;

    private double lowerLimitPrice;

    private String updateTime;

    private String tradingDay;

    private long volume;

    private long interest;

    public String getInstrumentId() {
        return instrumentId;
    }

    public void setInstrumentId(String instrumentId) {
//        Matcher matcher = pattern.matcher(instrumentId);
//        if(matcher.find()){
//            this.instrumentId = matcher.group(1).toUpperCase();
//        }else{
//            this.instrumentId = "UNKNOWN";
//        }
        this.instrumentId = instrumentId;
    }

    public double getLastPrice() {
        return lastPrice;
    }

    public void setLastPrice(double lastPrice) {
        this.lastPrice = lastPrice;
    }

    public double getOpenPrice() {
        return openPrice;
    }

    public void setOpenPrice(double openPrice) {
        this.openPrice = openPrice;
    }

    public double getHighestPrice() {
        return highestPrice;
    }

    public void setHighestPrice(double highestPrice) {
        this.highestPrice = highestPrice;
    }

    public double getLowestPrice() {
        return lowestPrice;
    }

    public void setLowestPrice(double lowestPrice) {
        this.lowestPrice = lowestPrice;
    }

    public double getUpperLimitPrice() {
        return upperLimitPrice;
    }

    public void setUpperLimitPrice(double upperLimitPrice) {
        this.upperLimitPrice = upperLimitPrice;
    }

    public double getLowerLimitPrice() {
        return lowerLimitPrice;
    }

    public void setLowerLimitPrice(double lowerLimitPrice) {
        this.lowerLimitPrice = lowerLimitPrice;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getTradingDay() {
        return tradingDay;
    }

    public void setTradingDay(String tradingDay) {
        this.tradingDay = tradingDay;
    }

    public long getVolume() {
        return volume;
    }

    public void setVolume(long volume) {
        this.volume = volume;
    }

    public long getInterest() {
        return interest;
    }

    public void setInterest(long interest) {
        this.interest = interest;
    }

    @Override
    public String toString() {
        return "Quotation{" +
                "instrumentId='" + instrumentId + '\'' +
                ", lastPrice=" + lastPrice +
                ", openPrice=" + openPrice +
                ", highestPrice=" + highestPrice +
                ", lowestPrice=" + lowestPrice +
                ", upperLimitPrice=" + upperLimitPrice +
                ", lowerLimitPrice=" + lowerLimitPrice +
                ", updateTime='" + updateTime + '\'' +
                ", tradingDay='" + tradingDay + '\'' +
                ", volume=" + volume +
                ", interest=" + interest +
                '}';
    }

    //    public static void main(String[] args) {
//        Quotation quotation = new Quotation();
//        quotation.setInstrumentId("TA234");
//        System.out.println(quotation.getInstrumentId());
//    }
}

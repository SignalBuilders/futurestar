package org.zhps.market.entity;

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

    private long ave5d;

    private long ave10d;

    private double lastHighest;

    private double lastLowest;

    private int hour;

    private int minute;

    private int second;

    public String getInstrumentId() {
        return instrumentId;
    }

    public void setInstrumentId(String instrumentId) {
        Matcher matcher = pattern.matcher(instrumentId);
        if(matcher.find()){
            this.instrumentId = matcher.group(1).toLowerCase();
        }else{
            this.instrumentId = "UNKNOWN";
        }
//        this.instrumentId = instrumentId;
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

    public long getAve5d() {
        return ave5d;
    }

    public void setAve5d(long ave5d) {
        this.ave5d = ave5d;
    }

    public long getAve10d() {
        return ave10d;
    }

    public void setAve10d(long ave10d) {
        this.ave10d = ave10d;
    }

    public double getLastHighest() {
        return lastHighest;
    }

    public void setLastHighest(double lastHighest) {
        this.lastHighest = lastHighest;
    }

    public double getLastLowest() {
        return lastLowest;
    }

    public void setLastLowest(double lastLowest) {
        this.lastLowest = lastLowest;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
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
                ", ave5d=" + ave5d +
                ", ave10d=" + ave10d +
                '}';
    }

    //    public static void main(String[] args) {
//        Quotation quotation = new Quotation();
//        quotation.setInstrumentId("TA234");
//        System.out.println(quotation.getInstrumentId());
//    }
}

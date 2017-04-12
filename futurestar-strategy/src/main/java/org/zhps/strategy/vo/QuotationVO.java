package org.zhps.strategy.vo;

import java.util.List;

/**
 * Copyright (c) 2012 Conversant Solutions. All rights reserved.
 * <p>
 * Created on 2017/2/18.
 */
public class QuotationVO {

    private String tradingDay;

    private String updateTime;

    private double last;

    private double open;

    private double highest;

    private double lowest;

    private double ave5d;

    private double ave10d;

    private double posPrice;

    private double last5d;

    private double last10d;

    private int posDirection;

    private boolean exec;

    private long volume;

    private long interest;

    private List<Double> lasts1m;

    private List<Double> lasts3m;

    private List<Double> lasts5m;

    private String type;

    private int hour;

    private int minute;

    private int second;

    private double lastHighest;

    private double lastLowest;

    public double getLast() {
        return last;
    }

    public void setLast(double last) {
        this.last = last;
    }

    public double getOpen() {
        return open;
    }

    public void setOpen(double open) {
        this.open = open;
    }

    public double getHighest() {
        return highest;
    }

    public void setHighest(double highest) {
        this.highest = highest;
    }

    public double getLowest() {
        return lowest;
    }

    public void setLowest(double lowest) {
        this.lowest = lowest;
    }

    public double getAve5d() {
        return ave5d;
    }

    public void setAve5d(double ave5d) {
        this.ave5d = ave5d;
    }

    public double getAve10d() {
        return ave10d;
    }

    public void setAve10d(double ave10d) {
        this.ave10d = ave10d;
    }

    public String getTradingDay() {
        return tradingDay;
    }

    public void setTradingDay(String tradingDay) {
        this.tradingDay = tradingDay;
    }

    public double getPosPrice() {
        return posPrice;
    }

    public void setPosPrice(double posPrice) {
        this.posPrice = posPrice;
    }

    public double getLast5d() {
        return last5d;
    }

    public void setLast5d(double last5d) {
        this.last5d = last5d;
    }

    public double getLast10d() {
        return last10d;
    }

    public void setLast10d(double last10d) {
        this.last10d = last10d;
    }

    public int getPosDirection() {
        return posDirection;
    }

    public void setPosDirection(int posDirection) {
        this.posDirection = posDirection;
    }

    public boolean isExec() {
        return exec;
    }

    public void setExec(boolean exec) {
        this.exec = exec;
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

    public List<Double> getLasts1m() {
        return lasts1m;
    }

    public void setLasts1m(List<Double> lasts1m) {
        this.lasts1m = lasts1m;
    }

    public List<Double> getLasts3m() {
        return lasts3m;
    }

    public void setLasts3m(List<Double> lasts3m) {
        this.lasts3m = lasts3m;
    }

    public List<Double> getLasts5m() {
        return lasts5m;
    }

    public void setLasts5m(List<Double> lasts5m) {
        this.lasts5m = lasts5m;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
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
}

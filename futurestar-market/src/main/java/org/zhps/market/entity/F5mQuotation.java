package org.zhps.market.entity;

/**
 * Copyright (c) 2012 Conversant Solutions. All rights reserved.
 * <p>
 * Created on 2017/6/4.
 */
public class F5mQuotation{

    private double open;

    private double highest;

    private double lowest;

    //last ave 5 kline of 5 minutes
    private double lave5ml;
    //last ave 5 kline of 5 minutes
    private double lave10ml;
    //current ave 5 kline of 5 minutes
    private double cave5ml;
    //current ave 10 kline of 5 minutes
    private double cave10ml;

    private boolean hasOpen = false;

    private boolean hasClose = false;

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

    public double getLave5ml() {
        return lave5ml;
    }

    public void setLave5ml(double lave5ml) {
        this.lave5ml = lave5ml;
    }

    public double getLave10ml() {
        return lave10ml;
    }

    public void setLave10ml(double lave10ml) {
        this.lave10ml = lave10ml;
    }

    public double getCave5ml() {
        return cave5ml;
    }

    public void setCave5ml(double cave5ml) {
        this.cave5ml = cave5ml;
    }

    public double getCave10ml() {
        return cave10ml;
    }

    public void setCave10ml(double cave10ml) {
        this.cave10ml = cave10ml;
    }

    public boolean isHasOpen() {
        return hasOpen;
    }

    public void setHasOpen(boolean hasOpen) {
        this.hasOpen = hasOpen;
    }

    public boolean isHasClose() {
        return hasClose;
    }

    public void setHasClose(boolean hasClose) {
        this.hasClose = hasClose;
    }
}

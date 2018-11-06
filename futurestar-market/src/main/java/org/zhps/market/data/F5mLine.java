package org.zhps.market.data;

import org.zhps.market.entity.F5mQuotation;
import org.zhps.market.entity.Quotation;
import org.zhps.market.storage.MemStorage;

import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;

/**
 * Copyright (c) 2012 Conversant Solutions. All rights reserved.
 * <p/>
 * Created on 2017/4/12.
 */
public class F5mLine {
    private static final PriceQueue price5mQueue = new PriceQueue();
    private static final PriceQueue price10mQueue = new PriceQueue();
    private static F5mQuotation f5mQuotation = null;
    private static double lave5ml = 0;
    private static double lave10ml = 0;

    public static void produce(Quotation quotation){
        String updateTime = quotation.getUpdateTime();
        quotation.setHour(Integer.parseInt(updateTime.split(":")[0]));
        quotation.setMinute(Integer.parseInt(updateTime.split(":")[1]));
        quotation.setSecond(Integer.parseInt(updateTime.split(":")[2]));

        open(quotation);
        if(f5mQuotation != null){
            run(quotation);
            close(quotation);
            store(quotation);
        }
    }

    private static void open(Quotation quotation){
        int mod = quotation.getMinute() % 5;
        if(mod == 0 && quotation.getSecond() == 0){
            f5mQuotation = new F5mQuotation();
            f5mQuotation.setOpen(quotation.getLatestPrice());
            f5mQuotation.setHighest(quotation.getLatestPrice());
            f5mQuotation.setLowest(quotation.getLatestPrice());
            f5mQuotation.setLave5ml(lave5ml);
            f5mQuotation.setLave10ml(lave10ml);
        }
        price5mQueue.popIfSize(5);
        price10mQueue.popIfSize(10);
    }

    private static void run(Quotation quotation){
        f5mQuotation.setHighest(Math.max(f5mQuotation.getHighest(), quotation.getLatestPrice()));
        f5mQuotation.setLowest(Math.min(f5mQuotation.getLowest(), quotation.getLatestPrice()));
        f5mQuotation.setCave5ml(price5mQueue.getAvePrice(quotation.getLatestPrice(), 5));
        f5mQuotation.setCave10ml(price10mQueue.getAvePrice(quotation.getLatestPrice(), 10));
    }

    private static void close(Quotation quotation){
        int mod = (quotation.getMinute() + 1) % 5;
        if(mod == 0 && quotation.getSecond() == 59){
            if(!f5mQuotation.isHasClose()){
                f5mQuotation.setHasClose(true);
                lave5ml = f5mQuotation.getCave5ml();
                lave10ml = f5mQuotation.getCave10ml();
                price5mQueue.addPrice(quotation.getLatestPrice());
                price10mQueue.addPrice(quotation.getLatestPrice());
            }
        }
    }

    private static void store(Quotation quotation){
//        MemStorage.storage(quotation, f5mQuotation);
    }
}

class PriceQueue extends LinkedList<Double>{
    private static final double ROD = 10.0; // reserves one decimal
    private static final double RTD = 100.0; // reserves two decimal

    public void popIfSize(int num){
        if(this.size() == num){
            this.poll();
        }
    }

    public void addPrice(double value){
        this.add(value);
    }

    public double getAvePrice(double latestPrice, int num){
        double sum = 0;
        int size = num - 1;

        if(this.size() < size){
            return sum;
        }

        for(int i = 0; i < size; i++){
            sum += this.get(i);
        }

        sum += latestPrice;

        return Math.round((sum / num) * ROD) / ROD;
    }
}

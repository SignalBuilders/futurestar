package org.zhps.market.util;

import com.alibaba.fastjson.JSON;
import org.hbase.async.*;
import org.zhps.base.hbase.BaseHbase;
import org.zhps.base.util.PropertiesUtil;

import java.text.NumberFormat;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * Copyright (c) 2012 Conversant Solutions. All rights reserved.
 * <p>
 * Created on 2018/7/7.
 */
public class AddEma2Quotation {
    final static HBaseClient hbaseClient = BaseHbase.gethBaseClient();

    public static void main(String[] args) {
        try {
            List<Double> ema5dQ = new ArrayList<>();
            List<Double> ema10dQ = new ArrayList<>();

            Scanner scanner = hbaseClient.newScanner(PropertiesUtil.HBASE_TABLE_CLOSE);
            scanner.setFilter(new RowFilter(CompareFilter.CompareOp.EQUAL, new SubstringComparator("rb05")));
            scanner.setReversed(true);
            ArrayList<ArrayList<KeyValue>> tds = null;
            while((tds = scanner.nextRows(1).joinUninterruptibly()) != null){
                for(ArrayList<KeyValue> keyValues : tds){
                    String key = new String(keyValues.get(0).key());
                    System.out.println(key);
                    for(KeyValue keyValue : keyValues){
                        String last = new String(keyValue.qualifier());
                        if(last.equalsIgnoreCase("last")){
                            double close = Double.parseDouble(new String(keyValue.value()));
                            double ema5d = 0;
                            double ema10d = 0;

                            ema5dQ.add(close);
                            ema10dQ.add(close);

                            if(ema5dQ.size() == 5){
                                ema5d = calculateEma5d(ema5dQ);
                                ema5dQ.remove(0);
                            }

                            if(ema10dQ.size() == 10){
                                ema10d = calculateEma10d(ema10dQ);
                                ema10dQ.remove(0);
                            }

//                            System.out.println(close);
//                            System.out.println(ema5d);
//                            System.out.println(ema10d);

                            write2Hbase(key, ema5d, ema10d);
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static double calculateEma5d(List<Double> ema5dQ){
        return calculateEma(ema5dQ);
    }

    private static double calculateEma10d(List<Double>  ema10dQ){
        return calculateEma(ema10dQ);
    }

    private static double calculateEma(List<Double> emaArr){
        double sumWeightClose = 0;
        double sumWeight = 0;
        for(int i = 1; i <= emaArr.size(); i++){
            sumWeightClose += emaArr.get(i - 1) * i;
            sumWeight += i;
        }
        return Math.round(sumWeightClose / sumWeight * 100) / 100.0;
    }

    private static void write2Hbase(String rowKey, Double ema5d, Double ema10d){
        byte[][] colBytes = {"ema5d".getBytes(),"ema10d".getBytes()};
        byte[][] valBytes = {String.valueOf(ema5d).getBytes(), String.valueOf(ema10d).getBytes()};
        PutRequest put = new PutRequest(PropertiesUtil.HBASE_TABLE_CLOSE.getBytes(), rowKey.getBytes(),
                PropertiesUtil.HBASE_TABLE_CLOSE_CF.getBytes(), colBytes, valBytes);
        hbaseClient.put(put);
    }
}

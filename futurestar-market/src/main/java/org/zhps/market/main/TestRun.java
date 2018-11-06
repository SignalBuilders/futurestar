package org.zhps.market.main;

import java.text.NumberFormat;

/**
 * Copyright (c) 2012 Conversant Solutions. All rights reserved.
 * <p>
 * Created on 2018/7/1.
 */
public class TestRun {
    public static void main(String[] args) {
//        double c1 = 3675;
//        double c2 = 3672;
//        double c3 = 3744;
//        double c4 = 3807;
//        double c5 = 3794;
//
//        double ema5 = (c1 * 1 + c2 * 2 + c3 * 3 + c4 * 4 + c5 * 5)/(1 + 2 + 3 + 4 + 5);
//        System.out.println(ema5);
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(2);
        System.out.println(1 / 8.0);
        System.out.println(Math.round(1 / 8.0 * 100) / 100.0);
    }
}

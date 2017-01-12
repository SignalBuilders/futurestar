package org.zhps.hjctp.jni;


import org.zhps.hjctp.entity.Iorder;
import org.zhps.hjctp.entity.Korder;
import org.zhps.hjctp.entity.Qorder;
import org.zhps.hjctp.spi.MdSpi;
import org.zhps.hjctp.spi.TraderSpi;

import java.util.List;

/**
 * Copyright (c) 2012 Conversant Solutions. All rights reserved.
 * <p/>
 * Created on 2016/12/6.
 */
public class NativeLoader {
    static{
        System.loadLibrary("hjctp");
    }
    //market
    public static native void createMdApi(String pszFlowPath, boolean bIsUsingUdp, boolean bIsMulticast);
    public static native void registerMdSpi(MdSpi mdSpi);
    public static native void registerMdFront(String frontAddress);
    public static native void registerMdLoginInfo(String brokerId, String investorId, String password);
    public static native void registerSubMarketData(String[] contracts, int iInstrumentID);
    public static native void connectMdServer();
    public static native String getTradingTay();
    public static native void stop();

    //trader
    public static native void createTraderApi(String pszFlowPath);
    public static native void registerTraderSpi(TraderSpi traderSpi);
    public static native void registerTraderFront(String frontAddress);
    public static native void registerTraderLoginInfo(String brokerId, String investorId, String password);
    public static native void connectTraderServer();

    public static native void insertOrder(Iorder iorder);
    public static native void killOrder(Korder korder);
    public static native void queryOrder(Qorder qorder);

    public static native int queryTradingAccount();
    public static native int queryInvestorPosition();
    public static native int queryInvestorPositionDetail();
}

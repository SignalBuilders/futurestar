package org.zhps.hjctp.api;

import org.zhps.hjctp.entity.Iorder;
import org.zhps.hjctp.entity.Korder;
import org.zhps.hjctp.entity.Qorder;
import org.zhps.hjctp.jni.NativeLoader;
import org.zhps.hjctp.spi.TraderSpi;

/**
 * Copyright (c) 2012 Conversant Solutions. All rights reserved.
 * <p/>
 * Created on 2017/1/8.
 */
public class TraderApi {
    public TraderApi(String pszFlowPath){
        NativeLoader.createTraderApi(pszFlowPath);
    }

    public void registerSpi(TraderSpi traderSpi){
        NativeLoader.registerTraderSpi(traderSpi);
    }

    public void registerFront(String frontAddress){
        NativeLoader.registerTraderFront(frontAddress);
    }

    public void registerLoginInfo(String brokerId, String investorId, String password){
        NativeLoader.registerTraderLoginInfo(brokerId, investorId, password);
    }

    public void connect(){
        NativeLoader.connectTraderServer();
    }

    public static int queryTradingAccount(){
        return NativeLoader.queryTradingAccount();
    }

    public int queryInvestorPosition(){
        return NativeLoader.queryInvestorPosition();
    }

    public int queryInvestorPositionDetail(){
        return NativeLoader.queryInvestorPositionDetail();
    }

    public void killOrder(Korder korder){
        NativeLoader.killOrder(korder);
    }

    public void insertOrder(Iorder iorder){
        NativeLoader.insertOrder(iorder);
    }

    public void queryOrder(Qorder qorder){
        NativeLoader.queryOrder(qorder);
    }
}

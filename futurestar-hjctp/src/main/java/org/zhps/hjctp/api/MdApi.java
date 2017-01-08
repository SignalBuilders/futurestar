package org.zhps.hjctp.api;


import org.zhps.hjctp.jni.NativeLoader;
import org.zhps.hjctp.spi.MdSpi;

/**
 * Copyright (c) 2012 Conversant Solutions. All rights reserved.
 * <p/>
 * Created on 2016/12/6.
 */
public class MdApi {

    public MdApi(String pszFlowPath, boolean bIsUsingUdp, boolean bIsMulticast){
        NativeLoader.createMdApi(pszFlowPath, bIsUsingUdp, bIsMulticast);
    };

    public void registerSpi(MdSpi mdSpi){
        NativeLoader.registerMdSpi(mdSpi);
    }

    public void registerFront(String frontAddress){
        NativeLoader.registerMdFront(frontAddress);
    }

    public void registerLoginInfo(String brokerId, String investorId, String password){
        NativeLoader.registerMdLoginInfo(brokerId, investorId, password);
    }

    public void registerSubMarketData(String[] contracts, int iInstrumentID){
        NativeLoader.registerSubMarketData(contracts, iInstrumentID);
    }

    public void connect(){
        NativeLoader.connectMdServer();
    }

    public String getTradingDay(){
        return NativeLoader.getTradingTay();
    }

    public void stop() {
        NativeLoader.stop();
    }
}

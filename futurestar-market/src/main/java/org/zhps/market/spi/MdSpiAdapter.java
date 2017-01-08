package org.zhps.market.spi;

import org.zhps.base.util.PropertiesUtil;
import org.zhps.hjctp.entity.*;
import org.zhps.hjctp.spi.MdSpi;
import org.zhps.market.producer.MarketProducer;

import java.io.BufferedWriter;
import java.io.IOException;

/**
 * Copyright (c) 2012 Conversant Solutions. All rights reserved.
 * <p/>
 * Created on 2016/12/7.
 */
public class MdSpiAdapter implements MdSpi {

    BufferedWriter bufWriter;

    MarketProducer marketProducer;

    public MdSpiAdapter(){

    }

    public MdSpiAdapter(MarketProducer marketProducer) {
        this.marketProducer = marketProducer;
    }

    @Override
    public void onFrontConnected() {
//        System.out.println("front connect success");
    }

    @Override
    public void onFrontDisconnected(int nReason) {

    }

    @Override
    public void onRspUserLogin(CThostFtdcRspUserLoginField pRspUserLogin, CThostFtdcRspInfoField pRspInfo,
                               int nRequestID, boolean bIsLast) {
        StringBuilder loginInfo = new StringBuilder("Login Success: ").append(pRspUserLogin.getTradingDay());
        System.out.println(loginInfo.toString());
    }

    @Override
    public void onRspUserLogout(CThostFtdcUserLogoutField pUserLogout, CThostFtdcRspInfoField pRspInfo, int nRequestID, boolean bIsLast) {

    }

    @Override
    public void onRspError(CThostFtdcRspInfoField pRspInfo, int nRequestID, boolean bIsLast) {

    }

    @Override
    public void onRspSubMarketData(CThostFtdcSpecificInstrumentField pSpecificInstrument, CThostFtdcRspInfoField pRspInfo, int nRequestID, boolean bIsLast) {

    }

    @Override
    public void onRspUnSubMarketData(CThostFtdcSpecificInstrumentField pSpecificInstrument, CThostFtdcRspInfoField pRspInfo, int nRequestID, boolean bIsLast) {

    }

    @Override
    public void onRspSubForQuoteRsp(CThostFtdcSpecificInstrumentField pSpecificInstrument, CThostFtdcRspInfoField pRspInfo, int nRequestID, boolean bIsLast) {

    }

    @Override
    public void onRspUnSubForQuoteRsp(CThostFtdcSpecificInstrumentField pSpecificInstrument, CThostFtdcRspInfoField pRspInfo, int nRequestID, boolean bIsLast) {

    }


    @Override
    public void onRtnDepthMarketData(CThostFtdcDepthMarketDataField pDepthMarketData) {
        StringBuilder markets = new StringBuilder(pDepthMarketData.getInstrumentId()).append(" ")
                .append(pDepthMarketData.getLastPrice()).append(" ")
                .append(pDepthMarketData.getOpenPrice()).append(" ")
                .append(pDepthMarketData.getUpperLimitPrice()).append(" ")
                .append(pDepthMarketData.getLowerLimitPrice()).append(" ")
                .append(pDepthMarketData.getUpdateTime()).append(" ")
                .append(pDepthMarketData.getTradingDay()).append(" ")
                .append(pDepthMarketData.getUpdateMillisec()).append(" ");
//        if(this.marketProducer != null){
//            marketProducer.send(PropertiesUtil.MK_TOPIC, markets.toString());
//        }

//        try {
//            bufWriter.newLine();
//            bufWriter.write(markets.toString());
//            bufWriter.flush();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//        }
        System.out.println(markets.toString());
//        System.out.println(pDepthMarketData.getClosePrice());
//        System.out.println(pDepthMarketData);
    }

    @Override
    public void onRtnForQuoteRsp(CThostFtdcForQuoteRspField pForQuoteRsp) {

    }

    @Override
    public void onResetBufferWriter(BufferedWriter bufWriter) {
        this.bufWriter = bufWriter;
    }
}
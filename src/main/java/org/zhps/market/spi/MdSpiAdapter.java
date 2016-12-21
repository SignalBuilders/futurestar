package org.zhps.market.spi;

import org.hjctp.entity.*;
import org.hjctp.spi.MdSpi;
import org.zhps.market.producer.MarketProducer;
import org.zhps.util.PropertiesUtil;

import java.io.BufferedWriter;

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
        System.out.println("front connect success");
    }

    @Override
    public void onFrontDisconnected(int nReason) {

    }

    @Override
    public void onRspUserLogin(CThostFtdcRspUserLoginField pRspUserLogin, CThostFtdcRspInfoField pRspInfo,
                               int nRequestID, boolean bIsLast) {
        System.out.println(pRspUserLogin);
        System.out.println(pRspInfo);
        System.out.println(nRequestID);
        System.out.println(bIsLast);
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
        StringBuilder markets = new StringBuilder(pDepthMarketData.getInstrumentId()).append(" ").append(pDepthMarketData.getLastPrice())
                .append(" ").append(pDepthMarketData.getUpdateTime());
        if(this.marketProducer != null){
            marketProducer.send(PropertiesUtil.MK_TOPIC, markets.toString());
        }
//        System.out.println(pDepthMarketData.getInstrumentId());
//        try {
//            bufWriter.newLine();
//            bufWriter.write(pDepthMarketData.getInstrumentId() + " " + pDepthMarketData.getLastPrice() + " " + pDepthMarketData.getUpdateTime());
//            bufWriter.flush();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//        }
        System.out.println(pDepthMarketData.getInstrumentId() + " " + pDepthMarketData.getLastPrice() + " " + pDepthMarketData.getUpdateTime() + " " + pDepthMarketData.getTradingDay());
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
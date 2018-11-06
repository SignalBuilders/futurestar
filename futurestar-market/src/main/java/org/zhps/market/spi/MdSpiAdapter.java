package org.zhps.market.spi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zhps.base.redis.BaseRedis;
import org.zhps.base.util.PropertiesUtil;
import org.zhps.hjctp.api.MdApi;
import org.zhps.hjctp.entity.*;
import org.zhps.hjctp.spi.MdSpi;
import org.zhps.market.data.DataFactory;
import org.zhps.market.producer.MarketProducer;
import redis.clients.jedis.Jedis;

import java.io.BufferedWriter;
import java.io.IOException;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * Copyright (c) 2012 Conversant Solutions. All rights reserved.
 * <p/>
 * Created on 2016/12/7.
 */
public class MdSpiAdapter implements MdSpi {

    private Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

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
        StringBuilder loginInfo = new StringBuilder("Login Success: ").append(pRspUserLogin.getTradingDay())
                .append(" Date: ").append(new Date());
        try(Jedis jedis = BaseRedis.getJedis()){
            jedis.set("traday", pRspUserLogin.getTradingDay());
            System.out.println(loginInfo.toString());
        }catch (Exception e){
            System.out.println("user login error......|error:" + e.getMessage());
        }
    }

    @Override
    public void onRspUserLogout(CThostFtdcUserLogoutField pUserLogout, CThostFtdcRspInfoField pRspInfo, int nRequestID, boolean bIsLast) {
        System.out.println("Logout...");
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
        String markets = new StringBuilder(pDepthMarketData.getInstrumentId()).append("|")
                .append(pDepthMarketData.getLastPrice()).append("|")
                .append(pDepthMarketData.getOpenPrice()).append("|")
                .append(pDepthMarketData.getHighestPrice()).append("|")
                .append(pDepthMarketData.getLowestPrice()).append("|")
                .append(pDepthMarketData.getUpperLimitPrice()).append("|")
                .append(pDepthMarketData.getLowerLimitPrice()).append("|")
                .append(pDepthMarketData.getUpdateTime()).append("|")
                .append(pDepthMarketData.getTradingDay()).append("|")
                .append(pDepthMarketData.getVolume()).append("|")
                .append(pDepthMarketData.getOpenInterest()).toString();

        System.out.println(markets);
//        logger.info(markets);


//        writeToKafka(markets);
//        writeToDisk(markets);
//        System.out.println(markets.toString());
        DataFactory.format(markets);
    }

    @Override
    public void onRtnForQuoteRsp(CThostFtdcForQuoteRspField pForQuoteRsp) {

    }

    @Override
    public void onResetBufferWriter(BufferedWriter bufWriter) {
        this.bufWriter = bufWriter;
    }

    /**
     *
     * @param markets
     */
    private void writeToKafka(String markets){
        if(this.marketProducer != null){
            marketProducer.send(PropertiesUtil.MK_TOPIC, markets.toString());
        }
    }

    /**
     *
     * @param markets
     */
    private void writeToDisk(String markets){
        try {
            bufWriter.newLine();
            bufWriter.write(markets.toString());
            bufWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
    }
}
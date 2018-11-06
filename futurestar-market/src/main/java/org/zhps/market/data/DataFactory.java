package org.zhps.market.data;

import org.zhps.base.util.PropertiesUtil;
import org.zhps.market.entity.Quotation;

import java.util.Queue;
import java.util.regex.Pattern;

/**
 * Copyright (c) 2012 Conversant Solutions. All rights reserved.
 * <p/>
 * Created on 2017/4/12.
 */
public class DataFactory {
    private static final Pattern VERTICAL = Pattern.compile("\\|");

    public static void format(String markets){
        Quotation quotation = assembleQuotation(markets);
        if(PropertiesUtil.MK_ENV.equalsIgnoreCase("prod")){
            String updateTime = quotation.getUpdateTime();
            int hour = Integer.parseInt(updateTime.split(":")[0]);
            if((hour >= 21 && hour <= 23) || (hour >= 9 && hour <= 15)){//open time
//                F5mLine.produce(quotation);
//                D1Line.produce(quotation);
                D1Line.produce(quotation.getInstrumentId(), markets);
            }
        }else{
//            F5mLine.produce(quotation);
//            D1Line.produce(quotation);
            D1Line.produce(quotation.getInstrumentId(), markets);
        }
    }

    /**
     *
     * @param markets
     * @return
     */
    private static Quotation assembleQuotation(String markets){
        String[] quoStr = VERTICAL.split(markets);
        Quotation quotation = new Quotation();
        quotation.setInstrumentId(quoStr[PropertiesUtil.MK_QUO_INSTRUMENTID]);
        quotation.setLatestPrice(Double.parseDouble(quoStr[PropertiesUtil.MK_QUO_LAST_PRICE]));
        quotation.setOpenPrice(Double.parseDouble(quoStr[PropertiesUtil.MK_QUO_OPEN_PRICE]));
        quotation.setHighestPrice(Double.parseDouble(quoStr[PropertiesUtil.MK_QUO_HIGHEST_PRICE]));
        quotation.setLowestPrice(Double.parseDouble(quoStr[PropertiesUtil.MK_QUO_LOWEST_PRICE]));
        quotation.setUpperLimitPrice(Double.parseDouble(quoStr[PropertiesUtil.MK_QUO_UPPERLIMIT_PRICE]));
        quotation.setLowerLimitPrice(Double.parseDouble(quoStr[PropertiesUtil.MK_QUO_LOWERLIMIT_PRICE]));
        quotation.setUpdateTime(quoStr[PropertiesUtil.MK_QUO_UPDATETIME]);
        quotation.setTradingDay(quoStr[PropertiesUtil.MK_QUO_TRADINGDAY]);
        quotation.setVolume(Long.parseLong(quoStr[PropertiesUtil.MK_QUO_VOLUME]));
        quotation.setInterest((long)Double.parseDouble(quoStr[PropertiesUtil.MK_QUO_INTEREST]));
        return quotation;
    }
}

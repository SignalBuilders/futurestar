package org.zhps.market.data;

import org.zhps.market.entity.Quotation;
import org.zhps.market.storage.DbStorage;
import org.zhps.market.storage.MemStorage;

/**
 * Copyright (c) 2012 Conversant Solutions. All rights reserved.
 * <p/>
 * Created on 2017/4/12.
 */
public class T3mLine {
    private static final int LAST_NUM = 10;

    public static void produce(Quotation quotation){
        String updateTime = quotation.getUpdateTime();
        int hour = Integer.parseInt(updateTime.split(":")[0]);
        int minute = Integer.parseInt(updateTime.split(":")[1]);
        int second = Integer.parseInt(updateTime.split(":")[2]);
        int mod = minute % 3;

        quotation.setHour(hour);
        quotation.setMinute(minute);
        quotation.setSecond(second);

        //open
        if(mod == 0 && second == 0){
            // TODO: 2017/4/5 save open to hbase,caculate ave3m5l, ave3m10l, write to redis
            double lastHighestPrice = DbStorage.readLastHighestPrice(LAST_NUM);
            double lastLowestPrice = DbStorage.readLastLowestPrice(LAST_NUM);
            quotation.setLastHighest(lastHighestPrice);
            quotation.setLastLowest(lastLowestPrice);
        }

        //close
        if(mod + 2 == minute && second == 59){
            /// TODO: 2017/4/5 save close to hbase
        }

        MemStorage.storage(quotation, null);
    }
}

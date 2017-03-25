package org.zhps.market.util;

import org.hbase.async.HBaseClient;
import org.hbase.async.PutRequest;
import org.zhps.base.hbase.BaseHbase;
import org.zhps.base.task.BaseTask;
import org.zhps.base.util.PropertiesUtil;
import org.zhps.hjctp.api.MdApi;
import org.zhps.hjctp.api.TraderApi;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Copyright (c) 2012 Conversant Solutions. All rights reserved.
 * <p>
 * Created on 2017/2/4.
 */
public class WriteTradingDayToHbase {

    public static void startTimer(final MdApi mdApi){
        final HBaseClient client = BaseHbase.gethBaseClient();
        TimerTask task = new TimerTask(){
            @Override
            public void run() {
//                PutRequest put = new PutRequest(PropertiesUtil.HBASE_TABLE_TRADAY.getBytes(),
//                        PropertiesUtil.HBASE_TABLE_TRADAY_ROW.getBytes(), PropertiesUtil.HBASE_TABLE_TRADAY_CF.getBytes(),
//                        mdApi.getTradingDay().getBytes(), "".getBytes());
//                client.put(put);
            }
        };
        BaseTask.startTask(task, PropertiesUtil.OPEN_MARKET_DATE, PropertiesUtil.MINUTES_1);
    }
}

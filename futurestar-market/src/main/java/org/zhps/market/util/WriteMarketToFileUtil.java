package org.zhps.market.util;

import org.zhps.base.task.BaseTask;
import org.zhps.base.util.PropertiesUtil;
import org.zhps.hjctp.api.MdApi;
import org.zhps.hjctp.spi.MdSpi;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Copyright (c) 2012 Conversant Solutions. All rights reserved.
 * <p/>
 * Created on 2016/12/16.
 */
public class WriteMarketToFileUtil {
    private static BufferedWriter bufWriter;
    private static MdSpi mdSpi;

    public static void registerMdSpi(MdSpi mdSpi){
        WriteMarketToFileUtil.mdSpi = mdSpi;
        initBufWriter();
    }

    public static void initBufWriter(){
        try {
            BufferedWriter bufWriter = new BufferedWriter(new FileWriter("d:\\market\\market-20161226.txt", true));
            mdSpi.onResetBufferWriter(bufWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void startTimer(final MdApi mdApi){
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    bufWriter = new BufferedWriter(new FileWriter("d:\\market\\market-" + mdApi.getTradingDay() + ".txt", true));
                    mdSpi.onResetBufferWriter(bufWriter);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        BaseTask.startTask(timerTask, PropertiesUtil.OPEN_MARKET_DATE, PropertiesUtil.HOURS_24);
    }
}

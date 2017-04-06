package org.zhps.strategy.main;

import org.zhps.base.redis.BaseRedis;
import org.zhps.base.task.BaseTask;
import org.zhps.base.util.PropertiesUtil;
import org.zhps.strategy.average.Average5d10d;
import org.zhps.strategy.vo.QuotationVO;
import redis.clients.jedis.Jedis;

import java.util.TimerTask;

/**
 * Copyright (c) 2012 Conversant Solutions. All rights reserved.
 * <p/>
 * Created on 2016/12/6.
 */
public class StrategyRun {
    final static Jedis jedis = BaseRedis.getJedis();

    public static void main(String[] args){
        TimerTask startTask = new TimerTask() {
            @Override
            public void run() {
                QuotationVO ave5d10dVO = new QuotationVO();
                String[] position = jedis.get("position").split("\\|");
                ave5d10dVO.setPosPrice(Double.parseDouble(position[1]));
                ave5d10dVO.setPosDirection(Integer.parseInt(position[2]));
                String[] open = jedis.get("open").split("\\|");
                ave5d10dVO.setLast5d(Double.parseDouble(open[3]));
                ave5d10dVO.setLast10d(Double.parseDouble(open[4]));
                String[] quotation = jedis.get("close").split("\\|");
                while(quotation[1].equals("14") && !ave5d10dVO.isExec()){
                    ave5d10dVO.setTradingDay(quotation[0]);
                    ave5d10dVO.setOpen(Double.parseDouble(quotation[2]));
                    ave5d10dVO.setHighest(Double.parseDouble(quotation[3]));
                    ave5d10dVO.setLowest(Double.parseDouble(quotation[4]));
                    ave5d10dVO.setLast(Double.parseDouble(quotation[5]));
                    ave5d10dVO.setAve5d(Double.parseDouble(quotation[6]));
                    ave5d10dVO.setAve10d(Double.parseDouble(quotation[7]));
                    ave5d10dVO = Average5d10d.exec(ave5d10dVO);
                    System.out.println(ave5d10dVO);
                    quotation = jedis.get("close").split("\\|");
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        BaseTask.startTask(startTask, PropertiesUtil.START_STRATEGY_DATE, PropertiesUtil.HOURS_24);
    }
}

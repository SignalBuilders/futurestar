package org.zhps.strategy.average;

import org.hbase.async.HBaseClient;
import org.hbase.async.PutRequest;
import org.zhps.base.hbase.BaseHbase;
import org.zhps.base.redis.BaseRedis;
import org.zhps.base.util.PropertiesUtil;
import org.zhps.strategy.util.LogMapUtil;
import org.zhps.strategy.vo.Ave5d10dResVO;
import org.zhps.strategy.vo.Ave5d10dVO;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (c) 2012 Conversant Solutions. All rights reserved.
 * <p>
 * Created on 2017/2/18.
 */
public class Average5d10d {
    public static final int RANGE_THRESHOLD = 76;
    public static final int OPEN_AVE5D_THRESHOLD=85;
    public static final int DIFF_AVE5D_AVE10D_THRESHOLD=5;
    final static Jedis jedis = BaseRedis.getJedis();
    final static HBaseClient hbaseClient = BaseHbase.gethBaseClient();

    public static Ave5d10dVO exec(Ave5d10dVO ave5d10dVO){
        double cloPrice = 0;//close price
        int posNum = 1;//position num
        double cur5d = 0;
        double cur10d = 0;
        double last = 0;
        double open = 0;
        double highest = 0;
        double lowest = 0;
        String tradingDay = null;
//        List<Ave5d10dVO> ave5d10dVOs = new ArrayList<Ave5d10dVO>();
//        for(Ave5d10dVO ave5d10dVO : ave5d10dVOs){
            cur5d = ave5d10dVO.getAve5d();
            cur10d = ave5d10dVO.getAve10d();
            last = ave5d10dVO.getLast();
            open = ave5d10dVO.getOpen();
            highest = ave5d10dVO.getHighest();
            lowest = ave5d10dVO.getLowest();
            tradingDay = ave5d10dVO.getTradingDay();
            Ave5d10dResVO ave5d10dResVO = null;
            if(ave5d10dVO.getPosPrice() == 0){//no position
                if(ave5d10dVO.getLast5d() <= ave5d10dVO.getLast10d()){//buy open
                    if(cur5d >= cur10d && cur5d > ave5d10dVO.getLast5d() && cur10d > ave5d10dVO.getLast10d() && last > cur5d && (open - cur5d) < OPEN_AVE5D_THRESHOLD && (cur5d - cur10d) <= DIFF_AVE5D_AVE10D_THRESHOLD){//buy open
                        ave5d10dVO.setExec(true);

                        ave5d10dVO.setPosPrice(last);
                        ave5d10dVO.setPosDirection(PropertiesUtil.TD_DIRECTION_BUY);

                        ave5d10dResVO = new Ave5d10dResVO();
                        ave5d10dResVO.setOperationType(Integer.parseInt(String.valueOf(PropertiesUtil.TD_OFFSET_FLAG_OPEN)));
                        ave5d10dResVO.setPosDirection(Integer.parseInt(String.valueOf(PropertiesUtil.TD_DIRECTION_BUY)));
                        ave5d10dResVO.setPrice(ave5d10dVO.getPosPrice());
                        jedis.set("position", tradingDay + "|" + last + "|" + PropertiesUtil.TD_DIRECTION_BUY);
                    }
                }else if(ave5d10dVO.getLast5d() >= ave5d10dVO.getLast10d()){//sell open
                    if(cur5d <= cur10d && cur5d < ave5d10dVO.getLast5d() && cur10d < ave5d10dVO.getLast10d() && last < cur5d && (cur5d - open) < OPEN_AVE5D_THRESHOLD && (cur10d - cur5d) <= DIFF_AVE5D_AVE10D_THRESHOLD){//sell open
                        ave5d10dVO.setExec(true);

                        ave5d10dVO.setPosPrice(last);
                        ave5d10dVO.setPosDirection(PropertiesUtil.TD_DIRECTION_SELL);

                        ave5d10dResVO = new Ave5d10dResVO();
                        ave5d10dResVO.setOperationType(Integer.parseInt(String.valueOf(PropertiesUtil.TD_OFFSET_FLAG_OPEN)));
                        ave5d10dResVO.setPosDirection(Integer.parseInt(String.valueOf(PropertiesUtil.TD_DIRECTION_SELL)));
                        ave5d10dResVO.setPrice(ave5d10dVO.getPosPrice());
                        jedis.set("position", tradingDay + "|" + last + "|" + PropertiesUtil.TD_DIRECTION_SELL);
                    }
                }
            }else if(ave5d10dVO.getPosPrice() != 0){//have position
                if(ave5d10dVO.getPosDirection() == PropertiesUtil.TD_DIRECTION_BUY){//sell close
                    if((open - RANGE_THRESHOLD) >= lowest){
                        ave5d10dVO.setExec(true);

                        cloPrice = open - RANGE_THRESHOLD;

                        ave5d10dResVO = new Ave5d10dResVO();
                        ave5d10dResVO.setOperationType(Integer.parseInt(String.valueOf(PropertiesUtil.TD_OFFSET_FLAG_CLOSE)));
                        ave5d10dResVO.setPosDirection(Integer.parseInt(String.valueOf(PropertiesUtil.TD_DIRECTION_SELL)));
                        ave5d10dResVO.setPrice(cloPrice);

                        LogMapUtil.closeProfitList.add(cloPrice - ave5d10dVO.getPosPrice());
                        ave5d10dVO.setPosPrice(0);

                        jedis.set("position", tradingDay + "|0|0");

                    }else if(last <= cur5d || cur5d < ave5d10dVO.getLast5d()){// || cur10d < last10d){
                        ave5d10dVO.setExec(true);

                        cloPrice = last;

                        ave5d10dResVO = new Ave5d10dResVO();
                        ave5d10dResVO.setOperationType(Integer.parseInt(String.valueOf(PropertiesUtil.TD_OFFSET_FLAG_CLOSE)));
                        ave5d10dResVO.setPosDirection(Integer.parseInt(String.valueOf(PropertiesUtil.TD_DIRECTION_SELL)));
                        ave5d10dResVO.setPrice(cloPrice);

                        LogMapUtil.closeProfitList.add(cloPrice - ave5d10dVO.getPosPrice());
                        ave5d10dVO.setPosPrice(0);

                        jedis.set("position", tradingDay + "|0|0");
                    }
                }else if(ave5d10dVO.getPosDirection() == PropertiesUtil.TD_DIRECTION_SELL){//buy close
                    ave5d10dVO.setExec(true);

                    if((open + RANGE_THRESHOLD) <= highest){
                        cloPrice = open + RANGE_THRESHOLD;

                        ave5d10dResVO = new Ave5d10dResVO();
                        ave5d10dResVO.setOperationType(Integer.parseInt(String.valueOf(PropertiesUtil.TD_OFFSET_FLAG_CLOSE)));
                        ave5d10dResVO.setPosDirection(Integer.parseInt(String.valueOf(PropertiesUtil.TD_DIRECTION_BUY)));
                        ave5d10dResVO.setPrice(cloPrice);

                        LogMapUtil.closeProfitList.add(ave5d10dVO.getPosPrice() - cloPrice);
                        ave5d10dVO.setPosPrice(0);

                        jedis.set("position", tradingDay + "|0|0");

                    }else if(last >= cur5d || cur5d > ave5d10dVO.getLast5d()){// || cur10d > last10d){
                        ave5d10dVO.setExec(true);

                        cloPrice = last;

                        ave5d10dResVO = new Ave5d10dResVO();
                        ave5d10dResVO.setOperationType(Integer.parseInt(String.valueOf(PropertiesUtil.TD_OFFSET_FLAG_CLOSE)));
                        ave5d10dResVO.setPosDirection(Integer.parseInt(String.valueOf(PropertiesUtil.TD_DIRECTION_BUY)));
                        ave5d10dResVO.setPrice(cloPrice);

                        LogMapUtil.closeProfitList.add(ave5d10dVO.getPosPrice() - cloPrice);
                        ave5d10dVO.setPosPrice(0);

                        jedis.set("position", tradingDay + "|0|0");
                    }
                }
            }
            if(ave5d10dResVO != null){
                ave5d10dResVO.setTradingDay(tradingDay);
                LogMapUtil.tradeList.add(ave5d10dResVO);
                byte[][] colBytes = {"direction".getBytes(),"operation".getBytes(),"price".getBytes()};
                byte[][] valBytes = {String.valueOf(ave5d10dResVO.getPosDirection()).getBytes(),String.valueOf(ave5d10dResVO.getOperationType()).getBytes()
                        ,String.valueOf(ave5d10dResVO.getPrice()).getBytes()};
                PutRequest put = new PutRequest(PropertiesUtil.HBASE_TABLE_POSITION.getBytes(), ("rm|" + (100000000 - Integer.parseInt(tradingDay)) + "|" + tradingDay).getBytes(),
                        PropertiesUtil.HBASE_TABLE_POSITION_CF.getBytes(), colBytes, valBytes);
                hbaseClient.put(put);
            }
            //backtracking
//            ave5d10dVO.setLast5d(cur5d);
//            ave5d10dVO.setLast10d(cur10d);
//        }
        return ave5d10dVO;
    }
}

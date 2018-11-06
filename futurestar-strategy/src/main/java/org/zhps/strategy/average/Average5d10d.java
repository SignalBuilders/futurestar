package org.zhps.strategy.average;

import com.alibaba.fastjson.JSON;
import org.hbase.async.HBaseClient;
import org.hbase.async.PutRequest;
import org.zhps.base.hbase.BaseHbase;
import org.zhps.base.redis.BaseRedis;
import org.zhps.base.util.PropertiesUtil;
import org.zhps.strategy.util.LogMapUtil;
import org.zhps.strategy.vo.Ave5d10dResVO;
import org.zhps.strategy.vo.QuotationVO;
import redis.clients.jedis.Jedis;

/**
 * Copyright (c) 2012 Conversant Solutions. All rights reserved.
 * <p>
 * Created on 2017/2/18.
 */
public class Average5d10d {
    //open
    public static final int DIFF_OPEN_AVE5D_OPEN_THRESHOLD=75;
    public static final int DIFF_CLOSE_AVE5D_THRESHOLD=10000;
    public static final int DIFF_AVE5D_AVE10D_THRESHOLD=22;
    public static final int DIFF_OPEN_CLOSE_THRESHOLD=200;
    //close
    public static final int RANGE_THRESHOLD = 110;
    public static final int DIFF_OPEN_AVE5D_CLOSE_THRESHOLD=160;
    public static final int DIFF_MAX_AVE5D_CLOSE_THRESHOLD=180;
    final static Jedis jedis = BaseRedis.getJedis();
    final static HBaseClient hbaseClient = BaseHbase.gethBaseClient();

    public static QuotationVO exec(QuotationVO quotationVO){
        double cloPrice = 0;//close price
        int posNum = 1;//position num
        double cur5d = 0;
        double cur10d = 0;
        double last = 0;
        double open = 0;
        double highest = 0;
        double lowest = 0;
        String tradingDay = null;
//        List<Ave5d10dVO> quotationVOs = new ArrayList<Ave5d10dVO>();
//        for(Ave5d10dVO quotationVO : quotationVOs){
            cur5d = quotationVO.getAve5d();
            cur10d = quotationVO.getAve10d();
            last = quotationVO.getLast();
            open = quotationVO.getOpen();
            highest = quotationVO.getHighest();
            lowest = quotationVO.getLowest();
            tradingDay = quotationVO.getTradingDay();
            Ave5d10dResVO ave5d10dResVO = null;
            if(quotationVO.getPosPrice() == 0){//no position
//                if(quotationVO.getLast5d() <= quotationVO.getLast10d()){//buy open
                    if(cur5d > cur10d && last > cur5d && cur5d > quotationVO.getLast5d() && cur10d > quotationVO.getLast10d()
                            && (last - cur5d) < DIFF_CLOSE_AVE5D_THRESHOLD
                            && (open - cur5d) < DIFF_OPEN_AVE5D_OPEN_THRESHOLD
                            && (cur5d - cur10d) <= DIFF_AVE5D_AVE10D_THRESHOLD
                            && (last - open) < DIFF_OPEN_CLOSE_THRESHOLD){//buy open
                        quotationVO.setExec(true);

                        quotationVO.setPosPrice(last);
                        quotationVO.setPosDirection(PropertiesUtil.TD_DIRECTION_BUY);

                        ave5d10dResVO = new Ave5d10dResVO();
                        ave5d10dResVO.setOperationType(Integer.parseInt(String.valueOf(PropertiesUtil.TD_OFFSET_FLAG_OPEN)));
                        ave5d10dResVO.setPosDirection(Integer.parseInt(String.valueOf(PropertiesUtil.TD_DIRECTION_BUY)));
                        ave5d10dResVO.setPrice(quotationVO.getPosPrice());
                        jedis.set("position", tradingDay + "|" + last + "|" + PropertiesUtil.TD_DIRECTION_BUY);
                    }else
//                }else if(quotationVO.getLast5d() >= quotationVO.getLast10d()){//sell open
                    if(cur5d < cur10d && last < cur5d && cur5d < quotationVO.getLast5d() && cur10d < quotationVO.getLast10d()
                            && (cur5d - last) < DIFF_CLOSE_AVE5D_THRESHOLD
                            && (cur5d - open) < DIFF_OPEN_AVE5D_OPEN_THRESHOLD
                            && (cur10d - cur5d) <= DIFF_AVE5D_AVE10D_THRESHOLD
                            && (open - last) < DIFF_OPEN_CLOSE_THRESHOLD){//sell open
                        quotationVO.setExec(true);

                        quotationVO.setPosPrice(last);
                        quotationVO.setPosDirection(PropertiesUtil.TD_DIRECTION_SELL);

                        ave5d10dResVO = new Ave5d10dResVO();
                        ave5d10dResVO.setOperationType(Integer.parseInt(String.valueOf(PropertiesUtil.TD_OFFSET_FLAG_OPEN)));
                        ave5d10dResVO.setPosDirection(Integer.parseInt(String.valueOf(PropertiesUtil.TD_DIRECTION_SELL)));
                        ave5d10dResVO.setPrice(quotationVO.getPosPrice());
                        jedis.set("position", tradingDay + "|" + last + "|" + PropertiesUtil.TD_DIRECTION_SELL);
                    }
//                }
            }else if(quotationVO.getPosPrice() != 0){//have position
                if(quotationVO.getPosDirection() == PropertiesUtil.TD_DIRECTION_BUY){//sell close
                    double diffMaxAve5dClosePrice = getDiffMaxAve5dClosePrice(quotationVO);
                    if((open - lowest) >= RANGE_THRESHOLD || diffMaxAve5dClosePrice <= highest){//(last - cur5d) >= DIFF_MAX_AVE5D_CLOSE_THRESHOLD){
                        quotationVO.setExec(true);

                        if((open - lowest) >= RANGE_THRESHOLD) {
                            cloPrice = open - RANGE_THRESHOLD;
//                        }else if((last - cur5d) >= DIFF_MAX_AVE5D_CLOSE_THRESHOLD){//real time
                        }else if(diffMaxAve5dClosePrice <= highest){//backtracking
                            cloPrice = diffMaxAve5dClosePrice;
                        }


                        ave5d10dResVO = new Ave5d10dResVO();
                        ave5d10dResVO.setOperationType(Integer.parseInt(String.valueOf(PropertiesUtil.TD_OFFSET_FLAG_CLOSE)));
                        ave5d10dResVO.setPosDirection(Integer.parseInt(String.valueOf(PropertiesUtil.TD_DIRECTION_SELL)));
                        ave5d10dResVO.setPrice(cloPrice);

                        LogMapUtil.closeProfitList.add(cloPrice - quotationVO.getPosPrice());
                        quotationVO.setPosPrice(0);

                        jedis.set("position", tradingDay + "|0|0");

                    }else if(last <= cur10d || cur5d < quotationVO.getLast5d()
                            || (open - cur5d) > DIFF_OPEN_AVE5D_CLOSE_THRESHOLD || cur5d < cur10d){ //|| cur10d < quotationVO.getLast10d()
                        quotationVO.setExec(true);

                        cloPrice = last;

                        ave5d10dResVO = new Ave5d10dResVO();
                        ave5d10dResVO.setOperationType(Integer.parseInt(String.valueOf(PropertiesUtil.TD_OFFSET_FLAG_CLOSE)));
                        ave5d10dResVO.setPosDirection(Integer.parseInt(String.valueOf(PropertiesUtil.TD_DIRECTION_SELL)));
                        ave5d10dResVO.setPrice(cloPrice);

                        LogMapUtil.closeProfitList.add(cloPrice - quotationVO.getPosPrice());
                        quotationVO.setPosPrice(0);

                        jedis.set("position", tradingDay + "|0|0");
                    }
                }else if(quotationVO.getPosDirection() == PropertiesUtil.TD_DIRECTION_SELL){//buy close
                    double diffMaxAve5dClosePrice = getDiffMaxAve5dClosePrice(quotationVO);
                    if((highest - open) >= RANGE_THRESHOLD || diffMaxAve5dClosePrice >= lowest){//(cur5d - lowest) >= DIFF_MAX_AVE5D_CLOSE_THRESHOLD){
                        quotationVO.setExec(true);

                        if((highest - open) >= RANGE_THRESHOLD) {
                            cloPrice = open + RANGE_THRESHOLD;
//                        }else if((cur5d - last) >= DIFF_MAX_AVE5D_CLOSE_THRESHOLD){//real time
                        }else if(diffMaxAve5dClosePrice >= lowest){//backtracking
                            cloPrice = diffMaxAve5dClosePrice;
                        }

                        ave5d10dResVO = new Ave5d10dResVO();
                        ave5d10dResVO.setOperationType(Integer.parseInt(String.valueOf(PropertiesUtil.TD_OFFSET_FLAG_CLOSE)));
                        ave5d10dResVO.setPosDirection(Integer.parseInt(String.valueOf(PropertiesUtil.TD_DIRECTION_BUY)));
                        ave5d10dResVO.setPrice(cloPrice);

                        LogMapUtil.closeProfitList.add(quotationVO.getPosPrice() - cloPrice);
                        quotationVO.setPosPrice(0);

                        jedis.set("position", tradingDay + "|0|0");

                    }else if(last >= cur10d || cur5d > quotationVO.getLast5d()
                            || (cur5d - open) > DIFF_OPEN_AVE5D_CLOSE_THRESHOLD || cur5d > cur10d){// || cur10d > quotationVO.getLast10d()
                        quotationVO.setExec(true);

                        cloPrice = last;

                        ave5d10dResVO = new Ave5d10dResVO();
                        ave5d10dResVO.setOperationType(Integer.parseInt(String.valueOf(PropertiesUtil.TD_OFFSET_FLAG_CLOSE)));
                        ave5d10dResVO.setPosDirection(Integer.parseInt(String.valueOf(PropertiesUtil.TD_DIRECTION_BUY)));
                        ave5d10dResVO.setPrice(cloPrice);

                        LogMapUtil.closeProfitList.add(quotationVO.getPosPrice() - cloPrice);
                        quotationVO.setPosPrice(0);

                        jedis.set("position", tradingDay + "|0|0");
                    }
                }
            }
            if(ave5d10dResVO != null){
                ave5d10dResVO.setTradingDay(tradingDay);
                LogMapUtil.tradeList.add(ave5d10dResVO);
//                byte[][] colBytes = {"direction".getBytes(),"operation".getBytes(),"price".getBytes()};
//                byte[][] valBytes = {String.valueOf(ave5d10dResVO.getPosDirection()).getBytes(),String.valueOf(ave5d10dResVO.getOperationType()).getBytes()
//                        ,String.valueOf(ave5d10dResVO.getPrice()).getBytes()};
//                PutRequest put = new PutRequest(PropertiesUtil.HBASE_TABLE_POSITION.getBytes(), ("rm|" + (100000000 - Integer.parseInt(tradingDay)) + "|" + tradingDay).getBytes(),
//                        PropertiesUtil.HBASE_TABLE_POSITION_CF.getBytes(), colBytes, valBytes);
//                hbaseClient.put(put);
            }
//            backtracking
//            quotationVO.setLast5d(cur5d);
//            quotationVO.setLast10d(cur10d);
//        }
        return quotationVO;
    }

    /**
     *
     * @param quotationVO
     * @return
     */
    private static double getDiffMaxAve5dClosePrice(QuotationVO quotationVO){
        int position = quotationVO.getPosDirection() - 48;
        double ave5d = quotationVO.getAve5d();
        double last = quotationVO.getLast();
        switch (position){
            case 0://sell close
//                (rtnPrice - last) / 5 + ave5d + DIFF_MAX_AVE5D_CLOSE_THRESHOLD <= rtnPrice;
                return (5 * DIFF_MAX_AVE5D_CLOSE_THRESHOLD + 5 * ave5d - last) / 4;
            case 1://buy close
                return (5 * ave5d - 5 * DIFF_MAX_AVE5D_CLOSE_THRESHOLD - last) / 4;
            default:
                break;
        }
        return 0;
    }
}

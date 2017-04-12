package org.zhps.strategy.kline;

import org.zhps.base.util.PropertiesUtil;
import org.zhps.strategy.vo.PositionVO;
import org.zhps.strategy.vo.QuotationVO;
import org.zhps.strategy.vo.StrategyVO;

import java.util.List;

/**
 * Copyright (c) 2012 Conversant Solutions. All rights reserved.
 * <p/>
 * Created on 2017/4/6.
 */
public class KlineProgressive {

    private final static int OPEN_K_NUM = 5;
    private final static int CLOSE_K_NUM = 2;

    private final static int PROFIT_GAP = 3;
    private final static int LOSS_GAP = 5;

    private final static int PRICE_GAP = 3;

    private final static int K_LINE_1M = 1;
    private final static int K_LINE_3M = 3;
    private final static int K_LINE_5M = 5;

    public static void exec(StrategyVO strategyVO){
        if(strategyVO.getPositionVO().getPrice() == 0){
            open(strategyVO);
        }else{
            close(strategyVO);
        }
    }

    /**
     *
     * @param strategyVO
     */
    private static void open(StrategyVO strategyVO){
        if(strategyVO.getQuotationVO().getType().equals("3m")){
            open3m(strategyVO);
        }else if(strategyVO.getQuotationVO().getType().equals("5m")){
//            open5m(quotationVO);
        }else if(strategyVO.getQuotationVO().getType().equals("1m")){
//            open1m(quotationVO);
        }
    }

    /**
     *
     * @param strategyVO
     */
    private static void close(StrategyVO strategyVO){
        if(strategyVO.getQuotationVO().getType().equals("3m")){
            close3m(strategyVO);
        }else if(strategyVO.getQuotationVO().getType().equals("5m")){
//            close5m(quotationVO);
        }else if(strategyVO.getQuotationVO().getType().equals("1m")){
//            close1m(quotationVO);
        }
    }

    /**
     *
     * @param strategyVO
     */
    private static void open3m(StrategyVO strategyVO){
        double lastHighest = strategyVO.getQuotationVO().getLastHighest();
        double lastLowest = strategyVO.getQuotationVO().getLastLowest();
        double curPrice = strategyVO.getQuotationVO().getLast();
        int curHour = strategyVO.getQuotationVO().getHour();
        int curMinute = strategyVO.getQuotationVO().getMinute();
        int curSecond = strategyVO.getQuotationVO().getSecond();
        boolean _buyOpen = false;
        boolean _sellOpen = false;

        if(curSecond == 58){
            if(curPrice > lastHighest){
                _buyOpen = true;
            }else if(curPrice < lastLowest){
                _sellOpen = true;
            }
        }

        if(_buyOpen || _sellOpen){
            PositionVO positionVO = strategyVO.getPositionVO();
            positionVO.setHour(curHour);
            positionVO.setMinute(curMinute);
            positionVO.setSecond(curSecond);
            positionVO.setPrice(curPrice);
            positionVO.setExec(true);
            if(_buyOpen){
                buyOpen(positionVO);
            }else if(_sellOpen){
                sellOpen(positionVO);
            }
        }
    }

    /**
     *
     * @param strategyVO
     */
    private static void close3m(StrategyVO strategyVO){
        int pDirection = strategyVO.getPositionVO().getDirection();
        final double pPrice = strategyVO.getPositionVO().getPrice();
        final double curPrice = strategyVO.getQuotationVO().getLast();
        double pHighest = strategyVO.getPositionVO().getHighest();
        double pLowest = strategyVO.getPositionVO().getLowest();
        boolean _close = false;

        if(pDirection == PropertiesUtil.TD_DIRECTION_BUY){
            if(curPrice - pPrice  > PROFIT_GAP || curPrice <= pLowest || pPrice - curPrice > LOSS_GAP){
                _close = true;
            }

            if(_close){
                initPosition(strategyVO);
                sellClose(curPrice);
            }
        }else if(pDirection == PropertiesUtil.TD_DIRECTION_SELL){
            if(pPrice - curPrice > PROFIT_GAP || curPrice >= pHighest || curPrice - pPrice > LOSS_GAP){
                _close = true;
            }

            if(_close){
                initPosition(strategyVO);
                buyClose(curPrice);
            }
        }
    }

    /**
     *
     * @param strategyVO
     * @param kLineType
     * @param candleEndOperate
     * @return
     */
    private static boolean candleEnd(StrategyVO strategyVO, int kLineType, int offsetType, CandleEndOperate candleEndOperate){
        int pMinute = strategyVO.getPositionVO().getMinute();
        int qMinute = strategyVO.getQuotationVO().getMinute();

        int count = 0;
        if(offsetType == PropertiesUtil.TD_OFFSET_FLAG_OPEN){
            for(int i = 0; i < OPEN_K_NUM; i++){
                candleEndOperate.run(count, i);
            }
            return count == OPEN_K_NUM ? true : false;
        }else if(offsetType == PropertiesUtil.TD_OFFSET_FLAG_CLOSE){
            qMinute = qMinute - pMinute < 0 ? 60 + qMinute : qMinute;
            if(qMinute - pMinute >= (CLOSE_K_NUM + 1) * kLineType){
                for(int i = 0; i < CLOSE_K_NUM; i++){
                    candleEndOperate.run(count, i);
                }
            }
            return count == CLOSE_K_NUM ? true : false;
        }
        return false;
    }

    /**
     *
     * @param positionVO
     */
    private static void buyOpen(PositionVO positionVO){
        positionVO.setDirection(PropertiesUtil.TD_DIRECTION_BUY);
        // TODO: 2017/4/7 trade 
    }

    /**
     *
     * @param positionVO
     */
    private static void sellOpen(PositionVO positionVO){
        positionVO.setDirection(PropertiesUtil.TD_DIRECTION_SELL);
        // TODO: 2017/4/7 trade
    }

    private static void sellClose(double price){
        // TODO: 2017/4/7 trade 

    }

    private static void buyClose(double price){

    }

    private static void initPosition(StrategyVO strategyVO){
        PositionVO positionVO = strategyVO.getPositionVO();
        positionVO.setPrice(0);
        positionVO.setHighest(0);
        positionVO.setLowest(0);
        positionVO.setDirection(0);
        positionVO.setHour(0);
        positionVO.setMinute(0);
        positionVO.setSecond(0);
        positionVO.setExec(false);
    }
}

interface CandleEndOperate{
    void run(int count, int index);
}

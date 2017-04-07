package org.zhps.strategy.average;

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
        final List<Double> lasts3m = strategyVO.getQuotationVO().getLasts3m();
        final double pPrice = strategyVO.getPositionVO().getPrice();
        int qSecond = strategyVO.getQuotationVO().getSecond();
        boolean _buyOpen = false;
        boolean _sellOpen = false;

        if(qSecond == 58){
            _buyOpen = candleEnd(strategyVO, K_LINE_3M, PropertiesUtil.TD_OFFSET_FLAG_OPEN, new CandleEndOperate() {
                @Override
                public void run(int count, int index) {
                    if(pPrice < lasts3m.get(index)){
                        count++;
                    }
                }
            });
        }

        if(!_buyOpen && qSecond == 58){
            _sellOpen = candleEnd(strategyVO, K_LINE_3M, PropertiesUtil.TD_OFFSET_FLAG_OPEN, new CandleEndOperate() {
                @Override
                public void run(int count, int index) {
                    if(pPrice > lasts3m.get(index)){
                        count++;
                    }
                }
            });
        }

        if(_buyOpen || _sellOpen){
            QuotationVO quotationVO = strategyVO.getQuotationVO();
            PositionVO positionVO = strategyVO.getPositionVO();
            positionVO.setExec(true);
            positionVO.setHour(quotationVO.getHour());
            positionVO.setMinute(quotationVO.getMinute());
            positionVO.setSecond(quotationVO.getSecond());
            positionVO.setPrice(quotationVO.getLast());
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
        final List<Double> lasts3m = strategyVO.getQuotationVO().getLasts3m();
        int pDirection = strategyVO.getPositionVO().getDirection();
        final double pPrice = strategyVO.getPositionVO().getPrice();
        final double lastPrice = strategyVO.getQuotationVO().getLast();
        int qSecond = strategyVO.getQuotationVO().getSecond();
        boolean _close = false;

        if(pDirection == PropertiesUtil.TD_DIRECTION_BUY){
            //compare price gap
            if(pPrice - lastPrice > PRICE_GAP){
                _close = true;
            }

            //candleEnd
            if(!_close && qSecond == 58){
                _close = candleEnd(strategyVO, K_LINE_3M, PropertiesUtil.TD_OFFSET_FLAG_CLOSE, new CandleEndOperate() {
                    @Override
                    public void run(int count, int index) {
                        if(lastPrice < lasts3m.get(index)){
                            count++;
                        }
                    }
                });
            }

            if(_close){
                initPosition(strategyVO);
                sellClose(lastPrice);
                open3m(strategyVO);
            }
        }else if(pDirection == PropertiesUtil.TD_DIRECTION_SELL){
            //compare price gap
            if(lastPrice - pPrice > PRICE_GAP){
                _close = true;
            }

            //candleEnd
            if(!_close && qSecond == 58){
                _close = candleEnd(strategyVO, K_LINE_3M, PropertiesUtil.TD_OFFSET_FLAG_CLOSE, new CandleEndOperate() {
                    @Override
                    public void run(int count, int index) {
                        if(lastPrice > lasts3m.get(index)){
                            count++;
                        }
                    }
                });
            }

            if(_close){
                initPosition(strategyVO);
                buyClose(lastPrice);
                open3m(strategyVO);
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
        positionVO.setHour(0);
        positionVO.setMinute(0);
        positionVO.setSecond(0);
        positionVO.setExec(false);
    }
}

interface CandleEndOperate{
    void run(int count, int index);
}

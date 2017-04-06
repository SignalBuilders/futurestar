package org.zhps.strategy.average;

import org.zhps.strategy.vo.QuotationVO;

import java.util.List;

/**
 * Copyright (c) 2012 Conversant Solutions. All rights reserved.
 * <p/>
 * Created on 2017/4/6.
 */
public class KlineProgressive {

    private final static int OPEN_K_NUM = 5;
    private final static int CLOSE_K_NUM = 2;

    public static void exec(QuotationVO quotationVO){
        if(quotationVO.getPosPrice() == 0){
            open(quotationVO);
        }else{
            close(quotationVO);
        }
    }

    /**
     *
     * @param quotationVO
     */
    private static void open(QuotationVO quotationVO){
        if(quotationVO.equals("3m")){
            open3m(quotationVO);
        }else if(quotationVO.equals("5m")){
//            open5m(quotationVO);
        }else if(quotationVO.equals("1m")){
//            open1m(quotationVO);
        }
    }

    /**
     *
     * @param quotationVO
     */
    private static void close(QuotationVO quotationVO){
        if(quotationVO.equals("3m")){
            close3m(quotationVO);
        }else if(quotationVO.equals("5m")){
//            close5m(quotationVO);
        }else if(quotationVO.equals("1m")){
//            close1m(quotationVO);
        }
    }

    /**
     *
     * @param quotationVO
     */
    private static void open3m(QuotationVO quotationVO){
        List<Double> lasts3m = quotationVO.getLasts3m();
        boolean min = false;
        boolean max = false;
        for(double tmpLast : lasts3m){
            if(quotationVO.getLast() < tmpLast){
                min = true;
                break;
            }else{
                max = true;
                break;
            }
        }

        if(min){
            sellOpen();
            return;
        }
        if(max){
            buyOpen();
            return;
        }
    }

    /**
     *
     * @param quotationVO
     */
    private static void close3m(QuotationVO quotationVO){

    }

    private static void sellOpen(){

    }

    private static void buyOpen(){

    }
}

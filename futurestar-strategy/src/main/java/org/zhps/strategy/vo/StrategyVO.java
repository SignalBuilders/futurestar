package org.zhps.strategy.vo;

import java.util.List;

/**
 * Copyright (c) 2012 Conversant Solutions. All rights reserved.
 * <p/>
 * Created on 2017/4/7.
 */
public class StrategyVO {
    private PositionVO positionVO = new PositionVO();

    private QuotationVO quotationVO = new QuotationVO();

    public PositionVO getPositionVO() {
        return positionVO;
    }

    public void setPositionVO(PositionVO positionVO) {
        this.positionVO = positionVO;
    }

    public QuotationVO getQuotationVO() {
        return quotationVO;
    }

    public void setQuotationVO(QuotationVO quotationVO) {
        this.quotationVO = quotationVO;
    }
}



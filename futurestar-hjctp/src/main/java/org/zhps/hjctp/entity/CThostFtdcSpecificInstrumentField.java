package org.zhps.hjctp.entity;

import java.io.Serializable;

/**
 * Copyright (c) 2012 Conversant Solutions. All rights reserved.
 * <p>
 * Created on 2016/12/11.
 */
public class CThostFtdcSpecificInstrumentField implements Serializable {

    String InstrumentID;

    public String getInstrumentID() {
        return InstrumentID;
    }

    public void setInstrumentID(String instrumentID) {
        InstrumentID = instrumentID;
    }

    @Override
    public String toString() {
        return new StringBuilder("CThostFtdcSpecificInstrumentField{")
                .append("InstrumentID='").append(InstrumentID).append("'")
                .append("}").toString();
    }
}

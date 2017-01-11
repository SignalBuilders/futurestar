package org.zhps.hjctp.entity;

import java.io.Serializable;

/**
 * Copyright (c) 2012 Conversant Solutions. All rights reserved.
 * <p>
 * Created on 2016/12/11.
 */
public class CThostFtdcRspInfoField implements Serializable {

    private int errorID;

    private String errorMsg;

    public int getErrorID() {
        return errorID;
    }

    public void setErrorID(int errorID) {
        this.errorID = errorID;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public String toString() {
        return new StringBuilder("CThostFtdcRspInfoField{")
                .append("errorID=").append(errorID)
                .append(", errorMsg='").append(errorMsg).append("'")
                .append("}").toString();
    }
}

package com.fintech.omnipe.UPIPayment.dto;

public class VPAVerifyData {

    int statuscode;
    String msg;
    int errorCode;
    String rpid;
    String liveID;
    String apiRequestID;
    String accountHolder;
    String accountNo;

    public int getStatuscode() {
        return statuscode;
    }

    public String getMsg() {
        return msg;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getRpid() {
        return rpid;
    }

    public String getLiveID() {
        return liveID;
    }

    public String getApiRequestID() {
        return apiRequestID;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public String getAccountNo() {
        return accountNo;
    }
}

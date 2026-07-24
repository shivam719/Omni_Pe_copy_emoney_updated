package com.fintech.omnipe.Api.Request;

public class UpdateUPIRequest {

    public String userID;

    public String sessionID;

    public String session;

    public String appid;

    public String imei;

    public String regKey;
    public String version;
    public String serialNo;
    public String loginTypeID;
    String tid;
    String bankStatus;


    public UpdateUPIRequest() {

    }

    public UpdateUPIRequest(String tid, String bankStatus, String userID, String loginTypeID, String appid, String imei, String regKey, String version, String serialNo, String sessionID, String session) {
        this.tid = tid;
        this.bankStatus = bankStatus;
        this.userID = userID;
        this.sessionID = sessionID;
        this.session = session;
        this.appid = appid;
        this.imei = imei;
        this.regKey = regKey;
        this.version = version;
        this.serialNo = serialNo;
        this.loginTypeID = loginTypeID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public void setRegKey(String regKey) {
        this.regKey = regKey;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public void setLoginTypeID(String loginTypeID) {
        this.loginTypeID = loginTypeID;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public void setBankStatus(String bankStatus) {
        this.bankStatus = bankStatus;
    }
}

package com.fintech.omnipe.Api.Request;

public class IncentiveDetailRequest {
    String oid;
    String userID;
    String sessionID;
    String session;
    String appid;
    String imei;
    String regKey;
    String version;
    String serialNo;
    String loginTypeID;

    public IncentiveDetailRequest(String oid, String userID, String loginTypeID, String appid, String imei, String regKey, String version, String serialNo, String sessionID, String session) {
        this.oid=oid;
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
}

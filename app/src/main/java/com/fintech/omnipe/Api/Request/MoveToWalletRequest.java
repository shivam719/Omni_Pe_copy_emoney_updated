package com.fintech.omnipe.Api.Request;

public class MoveToWalletRequest {

    String appid;
    String imei;
    String loginTypeID;
    String regKey;
    String serialNo;
    String session;
    String sessionID;
    String userID;
    String version;
    String actionType;
    String TransMode;
    String amount;
    int MTWID;
    int OID;

    public MoveToWalletRequest(String appid,String imei,String loginTypeID,String regKey,String serialNo,String session,String sessionID,String userID,String version,String actionType,String transMode,String amount,int MTWID,int OID) {
        this.appid = appid;
        this.imei = imei;
        this.loginTypeID = loginTypeID;
        this.regKey = regKey;
        this.serialNo = serialNo;
        this.session = session;
        this.sessionID = sessionID;
        this.userID = userID;
        this.version = version;
        this.actionType = actionType;
        this.amount = amount;
        TransMode = transMode;
        this.MTWID = MTWID;
        this.OID = OID;
    }
}

package com.fintech.omnipe.Api.Request;

public class IntiateUPIRequest {

    public String userID;

    public String sessionID;

    public String session;

    public String appid;

    public String imei;

    public String regKey;

    public String version;

    public String serialNo;
    public String loginTypeID;
    public String walletID, oid, amount, upiid;
    public int upgid;


    public IntiateUPIRequest(String walletID, String oid, String amount, String upiid, String userID, String loginTypeID, String appid, String imei, String regKey, String version, String serialNo, String sessionID,int upgid, String session) {
        this.walletID = walletID;
        this.oid = oid;
        this.amount = amount;
        this.upiid = upiid;
        this.userID = userID;
        this.sessionID = sessionID;
        this.upgid = upgid;
        this.session = session;
        this.appid = appid;
        this.imei = imei;
        this.regKey = regKey;
        this.version = version;
        this.serialNo = serialNo;
        this.loginTypeID = loginTypeID;
    }
}

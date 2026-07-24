package com.fintech.omnipe.AEPS.FingPay.dto;

public class OnboardingRequest {

    String oid;
    String outletID;
    String userID;
    String sessionID;
    String session;
    String appid;
    String imei;
    String regKey;
    String version;
    String serialNo;
    String loginTypeID;
    String otp;

    public OnboardingRequest(String otp, String oid, String outletID, String userID, String sessionID, String session, String appid, String imei, String regKey, String version, String serialNo, String loginTypeID) {
        this.otp = otp;
        this.oid = oid;
        this.outletID = outletID;
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

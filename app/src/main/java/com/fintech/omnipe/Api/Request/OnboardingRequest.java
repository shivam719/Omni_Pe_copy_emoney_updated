package com.fintech.omnipe.Api.Request;

public class OnboardingRequest {
    String oid;
    String otp;
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
    String OTPRefID;
    String pidData;
    boolean isBio;
    int bioAuthType;
    double latitude;
    double Lattitude;
    double Longitude;
    public OnboardingRequest(double latitude,double Lattitude,double Longitude,int bioAuthType,boolean isBio,String OTPRefID,String pidData,String oid, String otp, String outletID, String userID, String sessionID, String session, String appid, String imei, String regKey, String version, String serialNo, String loginTypeID) {
        this.latitude = latitude;
        this.Lattitude = Lattitude;
        this.Longitude = Longitude;
        this.bioAuthType = bioAuthType;
        this.isBio = isBio;
        this.OTPRefID = OTPRefID;
        this.pidData = pidData;
        this.oid = oid;
        this.otp = otp;
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

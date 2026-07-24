package com.fintech.omnipe.DMRPipe.dto;

import com.fintech.omnipe.Util.Senderobject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ValiSenderRequest {


    @SerializedName("senderRequest")
    @Expose
    private Senderobject senderRequest;

    @SerializedName("beneMobile")
    @Expose
    private String beneMobile;
    @SerializedName("accountNo")
    @Expose
    private String accountNo;
    @SerializedName("otp")
    @Expose
    private String otp;
    @SerializedName("userID")
    @Expose
    private String userID;
    @SerializedName("sessionID")
    @Expose
    private String sessionID;
    @SerializedName("session")
    @Expose
    private String session;
    @SerializedName("appid")
    @Expose
    private String appid;
    @SerializedName("imei")
    @Expose
    private String imei;
    @SerializedName("regKey")
    @Expose
    private String regKey;
    @SerializedName("version")
    @Expose
    private String version;
    @SerializedName("serialNo")
    @Expose
    private String serialNo;
    @SerializedName("loginTypeID")
    @Expose
    private String loginTypeID;


    public Senderobject getSenderRequest() {
        return senderRequest;
    }

    public void setSenderRequest(Senderobject senderRequest) {
        this.senderRequest = senderRequest;
    }

    public String getBeneMobile() {
        return beneMobile;
    }

    public void setBeneMobile(String beneMobile) {
        this.beneMobile = beneMobile;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getRegKey() {
        return regKey;
    }

    public void setRegKey(String regKey) {
        this.regKey = regKey;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getLoginTypeID() {
        return loginTypeID;
    }

    public void setLoginTypeID(String loginTypeID) {
        this.loginTypeID = loginTypeID;
    }


    public ValiSenderRequest(Senderobject senderRequest, String beneMobile, String accountNo, String otp, String userID, String sessionID, String session, String appid, String imei, String regKey, String version, String serialNo, String loginTypeID) {
        this.senderRequest = senderRequest;
        this.beneMobile = beneMobile;
        this.accountNo = accountNo;
        this.otp = otp;
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

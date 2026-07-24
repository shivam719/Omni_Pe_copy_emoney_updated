package com.fintech.omnipe.MicroATM.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InitiateMiniATMReq {

    @SerializedName("sDKType")
    @Expose
    private String sDKType;
    @SerializedName("oid")
    @Expose
    private String oid;
    @SerializedName("amount")
    @Expose
    private String amount;
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
    private String Longitude;
    private String Lattitude;

    public InitiateMiniATMReq(String sDKType, String oid, String amount, String userID, String sessionID, String session, String appid, String imei, String regKey, String version, String serialNo, String loginTypeID) {
        this.sDKType = sDKType;
        this.oid = oid;
        this.amount = amount;
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

    public InitiateMiniATMReq(String Lattitude, String Longitude, String sDKType, String oid, String amount, String userID, String loginTypeID, String appid, String imei, String regKey, String version, String serialNo, String sessionID, String session) {
        this.Lattitude = Lattitude;
        this.Longitude = Longitude;
        this.sDKType = sDKType;
        this.oid = oid;
        this.amount = amount;
        this.userID = userID;
        this.loginTypeID = loginTypeID;
        this.appid = appid;
        this.imei = imei;
        this.regKey = regKey;
        this.version = version;
        this.serialNo = serialNo;
        this.sessionID = sessionID;
        this.session = session;
    }

    public String getsDKType() {
        return sDKType;
    }

    public void setsDKType(String sDKType) {
        this.sDKType = sDKType;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
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
}

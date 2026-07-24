package com.fintech.omnipe.Util;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginRequest {
    @SerializedName("loginTypeID")
    @Expose
    private Integer loginTypeID;
    @SerializedName("userID")
    @Expose
    private Object userID;
    @SerializedName("password")
    @Expose
    private Object password;
    @SerializedName("domain")
    @Expose
    private Object domain;
    @SerializedName("appid")
    @Expose
    private Object appid;
    @SerializedName("imei")
    @Expose
    private Object imei;
    @SerializedName("regKey")
    @Expose
    private Object regKey;
    @SerializedName("version")
    @Expose
    private Object version;
    @SerializedName("serialNo")
    @Expose
    private Object serialNo;
    String Longitude;

    String Latitude;
    @SerializedName("Mobile")
    @Expose
    private String Mobile;
    @SerializedName("Param")
    @Expose
    private ParamRequest Param;

    public LoginRequest(Integer loginTypeID, Object userID, Object password, Object domain, Object appid, Object imei, Object regKey, Object version, Object serialNo, String Latitude, String Longitude) {
        this.loginTypeID = loginTypeID;
        this.userID = userID;
        this.password = password;
        this.domain = domain;
        this.appid = appid;
        this.imei = imei;
        this.regKey = regKey;
        this.version = version;
        this.serialNo = serialNo;
        this.Latitude = Latitude;
        this.Longitude = Longitude;

        /*{"userID":0,"sessionID":0,"session":null,"appid":null,"imei":null,"regKey":null,"version":null,"serialNo":null,"loginTypeID":0}*/
    }

    public LoginRequest(int loginTypeID, String Mobile, ParamRequest Param, Object domain, Object appid, Object imei, Object regKey, Object version, Object serialNo, String Latitude, String Longitude) {
        this.loginTypeID = loginTypeID;
        this.Mobile = Mobile;
        this.Param = Param;
        this.domain = domain;
        this.appid = appid;
        this.imei = imei;
        this.regKey = regKey;
        this.version = version;
        this.serialNo = serialNo;
        this.Latitude = Latitude;
        this.Longitude = Longitude;
    }




    public Integer getLoginTypeID() {
        return loginTypeID;
    }

    public void setLoginTypeID(Integer loginTypeID) {
        this.loginTypeID = loginTypeID;
    }

    public Object getUserID() {
        return userID;
    }

    public void setUserID(Object userID) {
        this.userID = userID;
    }

    public Object getPassword() {
        return password;
    }

    public void setPassword(Object password) {
        this.password = password;
    }

    public Object getDomain() {
        return domain;
    }

    public void setDomain(Object domain) {
        this.domain = domain;
    }

    public Object getAppid() {
        return appid;
    }

    public void setAppid(Object appid) {
        this.appid = appid;
    }

    public Object getImei() {
        return imei;
    }

    public void setImei(Object imei) {
        this.imei = imei;
    }

    public Object getRegKey() {
        return regKey;
    }

    public void setRegKey(Object regKey) {
        this.regKey = regKey;
    }

    public Object getVersion() {
        return version;
    }

    public void setVersion(Object version) {
        this.version = version;
    }

    public Object getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(Object serialNo) {
        this.serialNo = serialNo;
    }
}

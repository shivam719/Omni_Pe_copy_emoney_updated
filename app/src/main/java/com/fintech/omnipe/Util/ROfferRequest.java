package com.fintech.omnipe.Util;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ROfferRequest {


    @SerializedName("appid")
    @Expose
    public String appid = "";
    @SerializedName("imei")
    @Expose
    public String imei = "";
    @SerializedName("regKey")
    @Expose
    public String regKey = "";
    @SerializedName("version")
    @Expose
    public String version = "";
    @SerializedName("serialNo")
    @Expose
    public String serialNo = "";
    @SerializedName("oid")
    @Expose
    private String oid;
    @SerializedName("accountNo")
    @Expose
    private String accountNo;
    @SerializedName("userID")
    @Expose
    public String userID;
    @SerializedName("sessionID")
    @Expose
    public String sessionID;
    @SerializedName("session")
    @Expose
    public String session;
    @SerializedName("loginTypeID")
    @Expose
    public String loginTypeID;

    public ROfferRequest(String oid, String accountNo, String appid, String imei, String regKey, String version, String serialNo, String userID, String sessionID, String session, String loginTypeID) {
        this.oid = oid;
        this.accountNo = accountNo;
        this.appid = appid;
        this.imei = imei;
        this.regKey = regKey;
        this.version = version;
        this.serialNo = serialNo;
        this.userID = userID;
        this.sessionID = sessionID;
        this.session = session;
        this.loginTypeID = loginTypeID;
    }
}

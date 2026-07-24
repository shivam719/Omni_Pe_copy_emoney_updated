package com.fintech.omnipe.DashBoard.UI;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UDetailByMobRequest {
    @SerializedName("userID")
    @Expose
    public String userID;
    @SerializedName("sessionID")
    @Expose
    public String sessionID;
    @SerializedName("session")
    @Expose
    public String session;
    @SerializedName("appid")
    @Expose
    public String appid;
    @SerializedName("imei")
    @Expose
    public String imei;
    @SerializedName("regKey")
    @Expose
    public String regKey;
    @SerializedName("version")
    @Expose
    public String version;
    @SerializedName("serialNo")
    @Expose
    public String serialNo;
    @SerializedName("loginTypeID")
    @Expose
    public String loginTypeID;
    @SerializedName("mobileNo")
    @Expose
    private String mobileNo;

    public UDetailByMobRequest(String userID, String loginTypeID, String appid, String imei, String regKey, String version, String serialNo, String sessionID, String session, String mobileNo) {
        this.userID = userID;
        this.loginTypeID = loginTypeID;
        this.appid = appid;
        this.imei = imei;
        this.regKey = regKey;
        this.version = version;
        this.serialNo = serialNo;
        this.sessionID = sessionID;
        this.session = session;
        this.mobileNo = mobileNo;
    }
}


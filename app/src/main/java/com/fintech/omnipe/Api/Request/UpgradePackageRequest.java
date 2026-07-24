package com.fintech.omnipe.Api.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpgradePackageRequest {
    String uid;
    int availablePackageId;
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




    public UpgradePackageRequest(String uid, int availablePackageId, String userID, String loginTypeID, String appid, String imei, String regKey, String version, String serialNo, String sessionID, String session) {
        this.uid = uid;
        this.availablePackageId=availablePackageId;
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

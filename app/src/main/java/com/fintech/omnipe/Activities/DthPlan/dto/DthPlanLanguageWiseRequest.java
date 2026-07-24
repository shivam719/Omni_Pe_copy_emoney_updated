package com.fintech.omnipe.Activities.DthPlan.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DthPlanLanguageWiseRequest {


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
    @SerializedName("Language")
    @Expose
    private String Language;
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

    public DthPlanLanguageWiseRequest(String oid, String Language, String accountNo, String appid, String imei, String regKey, String version, String serialNo,
                                      String userID,String sessionID,String session,String loginTypeID) {
        this.oid = oid;
        this.Language = Language;
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

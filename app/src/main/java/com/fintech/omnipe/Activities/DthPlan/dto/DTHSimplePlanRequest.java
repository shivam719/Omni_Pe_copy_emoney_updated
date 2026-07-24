package com.fintech.omnipe.Activities.DthPlan.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DTHSimplePlanRequest {

    public DTHSimplePlanRequest(String oid, String appid, String imei, String regKey, String version, String serialNo, String loginTypeID) {
        this.oid = oid;
        this.appid = appid;
        this.imei = imei;
        this.regKey = regKey;
        this.version = version;
        this.serialNo = serialNo;
        this.loginTypeID = loginTypeID;
    }

    @SerializedName("OID")
    @Expose
    private String oid;
    @SerializedName("APPID")
    @Expose
    private String appid;
    @SerializedName("IMEI")
    @Expose
    private String imei;
    @SerializedName("RegKey")
    @Expose
    private String regKey;
    @SerializedName("Version")
    @Expose
    private String version;
    @SerializedName("SerialNo")
    @Expose
    private String serialNo;
    @SerializedName("LoginTypeID")
    @Expose
    private String loginTypeID;

}

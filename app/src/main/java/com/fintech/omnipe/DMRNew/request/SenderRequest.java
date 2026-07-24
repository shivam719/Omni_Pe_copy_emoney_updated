package com.fintech.omnipe.DMRNew.request;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.fintech.omnipe.DMRNew.Data.DMTParam;

public class SenderRequest {

    String  sid;
    @SerializedName("latitude")
    @Expose
    public double latitude;
    @SerializedName("Lattitude")
    @Expose
    public double Lattitude;
    @SerializedName("Longitude")
    @Expose
    public double Longitude;
    @SerializedName("oid")
    @Expose
    private int oid;
    @SerializedName("userID")
    @Expose
    private String userID;
    @SerializedName("Mobile")
    @Expose
    private String Mobile;
    @SerializedName("loginTypeID")
    @Expose
    private String loginTypeID;
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
    @SerializedName("sessionID")
    @Expose
    private String sessionID;
    @SerializedName("session")
    @Expose
    private String session;
    @SerializedName("Param")
    @Expose
    private DMTParam Param;
    public SenderRequest(double latitude, double Lattitude, double Longitude, int oid, String Mobile, String sid, DMTParam Param, String userID, String loginTypeID, String appid, String imei,
                         String regKey, String version, String serialNo, String sessionID, String session) {
        this.Lattitude = Lattitude;
        this.latitude = latitude;
        this.Longitude = Longitude;
        this.oid = oid;
        this.Mobile = Mobile;
        this.sid = sid;
        this.Param = Param;
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
}


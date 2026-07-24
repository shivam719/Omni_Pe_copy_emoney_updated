package com.fintech.omnipe.MicroATM.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateMiniBankStatusReq {

    @SerializedName("tid")
    @Expose
    private String tid;
    @SerializedName("vendorID")
    @Expose
    private String vendorID;
    @SerializedName("aPIStatus")
    @Expose
    private String aPIStatus;
    @SerializedName("remark")
    @Expose
    private String remark;

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
    private String Lattitude;
    private String Longitude;
    private String accountNo, bankName;


    public UpdateMiniBankStatusReq(String tid, String vendorID, String aPIStatus, String remark, String userID, String sessionID, String session, String appid, String imei, String regKey, String version, String serialNo, String loginTypeID) {
        this.tid = tid;
        this.vendorID = vendorID;
        this.aPIStatus = aPIStatus;
        this.remark = remark;
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

    public UpdateMiniBankStatusReq(String Lattitude, String Longitude, String accountNo, String bankName, String tid, String vendorID, String aPIStatus, String remark, String userID, String loginTypeID, String appid, String imei, String regKey, String version, String serialNo, String sessionID, String session) {

        this.Lattitude = Lattitude;
        this.Longitude = Longitude;
        this.accountNo = accountNo;
        this.bankName = bankName;
        this.tid = tid;
        this.vendorID = vendorID;
        this.aPIStatus = aPIStatus;
        this.remark = remark;
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

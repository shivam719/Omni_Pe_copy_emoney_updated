package com.fintech.omnipe.Api.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RefundLogRequest {

    @SerializedName("topRows")
    @Expose
    private Integer topRows;
    @SerializedName("criteria")
    @Expose
    private Integer criteria;
    @SerializedName("criteriaText")
    @Expose
    private String criteriaText;

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("fromDate")
    @Expose
    private String fromDate;
    @SerializedName("toDate")
    @Expose
    private String toDate;

    @SerializedName("dateType")
    @Expose
    private Integer dateType;

    @SerializedName("transactionID")
    @Expose
    private String transactionID;

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
    @SerializedName("oid")
    @Expose
    private String oid;

    public RefundLogRequest(Integer topRows, Integer criteria, String criteriaText, Integer status, String fromDate, String toDate, Integer dateType, String transactionID, String userID, String sessionID, String session, String appid, String imei, String regKey, String version, String serialNo, String loginTypeID) {
        this.topRows = topRows;
        this.criteria = criteria;
        this.criteriaText = criteriaText;
        this.status = status;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.dateType = dateType;
        this.transactionID = transactionID;
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


    public RefundLogRequest(String oid, Integer topRows, Integer criteria, String criteriaText, Integer status, String fromDate, String toDate, Integer dateType, String transactionID, String userID, String sessionID, String session, String appid, String imei, String regKey, String version, String serialNo, String loginTypeID) {
        this.oid=oid;
        this.topRows = topRows;
        this.criteria = criteria;
        this.criteriaText = criteriaText;
        this.status = status;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.dateType = dateType;
        this.transactionID = transactionID;
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

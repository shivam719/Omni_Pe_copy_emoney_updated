package com.fintech.omnipe.AccountSettlement.API;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateSettlementAccountRequest {

    @SerializedName("BankID")
    @Expose
    private int BankID;
    @SerializedName("BankName")
    @Expose
    private String BankName;;
    @SerializedName("UpdateID")
    @Expose
    private int UpdateID;
    @SerializedName("UTR")
    @Expose
    private String UTR;
    @SerializedName("ifsc")
    @Expose
    private String ifsc;
    @SerializedName("AccountNumber")
    @Expose
    private String AccountNumber;
    @SerializedName("AccountHolder")
    @Expose
    private String AccountHolder;
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



    public UpdateSettlementAccountRequest(String userID, String loginTypeID, String appid, String imei, String regKey, String version, String serialNo, String sessionID, String session, String AccountHolder, String AccountNumber, int BankID, String BankName, int UpdateID, String ifsc) {
        this.userID = userID;
        this.loginTypeID = loginTypeID;
        this.appid = appid;
        this.imei = imei;
        this.regKey = regKey;
        this.version = version;
        this.serialNo = serialNo;
        this.sessionID = sessionID;
        this.session = session;
        this.AccountHolder = AccountHolder;
        this.AccountNumber = AccountNumber;
        this.BankID = BankID;
        this.BankName = BankName;
        this.UpdateID = UpdateID;
        this.ifsc = ifsc;
    }

    public UpdateSettlementAccountRequest(String userID, String loginTypeID, String appid, String imei, String regKey, String version, String serialNo, String sessionID, String session, int UpdateID) {
        this.userID = userID;
        this.loginTypeID = loginTypeID;
        this.appid = appid;
        this.imei = imei;
        this.regKey = regKey;
        this.version = version;
        this.serialNo = serialNo;
        this.sessionID = sessionID;
        this.session = session;
        this.UpdateID = UpdateID;
    }

    public UpdateSettlementAccountRequest(String userID, String loginTypeID, String appid, String imei, String regKey, String version, String serialNo, String sessionID, String session, int UpdateID, String UTR) {
        this.userID = userID;
        this.loginTypeID = loginTypeID;
        this.appid = appid;
        this.imei = imei;
        this.regKey = regKey;
        this.version = version;
        this.serialNo = serialNo;
        this.sessionID = sessionID;
        this.session = session;
        this.UpdateID = UpdateID;
        this.UTR = UTR;
    }
}

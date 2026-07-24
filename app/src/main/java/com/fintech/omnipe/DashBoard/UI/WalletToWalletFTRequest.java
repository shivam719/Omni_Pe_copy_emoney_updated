package com.fintech.omnipe.DashBoard.UI;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WalletToWalletFTRequest  {
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
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("remark")
    @Expose
    private String remark;
    @SerializedName("walletID")
    @Expose
    private Integer walletID;
    @SerializedName("uid")
    @Expose
    public int uid;
    public WalletToWalletFTRequest(String userID, String loginTypeID, String appid, String imei, String regKey, String version, String serialNo, String sessionID, String session,int uid, String amount, String remark, Integer walletID) {
        this.userID = userID;
        this.loginTypeID = loginTypeID;
        this.appid = appid;
        this.imei = imei;
        this.regKey = regKey;
        this.version = version;
        this.serialNo = serialNo;
        this.sessionID = sessionID;
        this.session = session;
        this.amount = amount;
        this.remark = remark;
        this.uid = uid;
        this.walletID = walletID;
    }
}

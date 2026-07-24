package com.fintech.omnipe.Api.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AchieveTargetRequest  extends BasicRequest {
    @SerializedName("isTotal")
    @Expose
    private boolean isTotal;


    public AchieveTargetRequest(boolean isTotal, String userID, String loginTypeID, String appid, String imei, String regKey, String version, String serialNo, String sessionID, String session) {
        this.isTotal = isTotal;
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


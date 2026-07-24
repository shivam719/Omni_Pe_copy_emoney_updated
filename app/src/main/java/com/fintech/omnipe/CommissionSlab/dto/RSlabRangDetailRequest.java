package com.fintech.omnipe.CommissionSlab.dto;

import com.fintech.omnipe.Api.Request.BasicRequest;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class RSlabRangDetailRequest extends BasicRequest {

    @SerializedName("oid")
    @Expose
    private Integer oid;

    public RSlabRangDetailRequest(int oid, String userID, String loginTypeID, String appid, String imei, String regKey, String version, String serialNo,String sessionID, String session) {
        this.userID = userID;
        this.loginTypeID = loginTypeID;
        this.appid = appid;
        this.imei = imei;
        this.regKey = regKey;
        this.version = version;
        this.serialNo = serialNo;
        this.oid = oid;
        this.sessionID = sessionID;
        this.session = session;
    }

    public Integer getOid() {
        return oid;
    }

    public void setOid(Integer oid) {
        this.oid = oid;
    }
}


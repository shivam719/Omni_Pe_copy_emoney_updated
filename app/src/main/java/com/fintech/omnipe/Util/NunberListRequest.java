package com.fintech.omnipe.Util;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NunberListRequest {
    @SerializedName("appid")
    @Expose
    private Object appid;
    @SerializedName("imei")
    @Expose
    private Object imei;
    @SerializedName("regKey")
    @Expose
    private Object regKey;
    @SerializedName("version")
    @Expose
    private Object version;
    @SerializedName("serialNo")
    @Expose
    private Object serialNo;

    public NunberListRequest(Object appid, Object imei, Object regKey, Object version, Object serialNo) {
        this.appid = appid;
        this.imei = imei;
        this.regKey = regKey;
        this.version = version;
        this.serialNo = serialNo;
    }
}

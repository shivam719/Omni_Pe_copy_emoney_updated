package com.fintech.omnipe.Activities.DthCustomerInfo.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DTHInfoResponse {
    @SerializedName("statuscode")
    @Expose
    private String statuscode;

    @SerializedName("msg")
    @Expose
    private String msg;

    @SerializedName("data")
    @Expose
    private DTHInfoData data;
    @SerializedName("dataPA")
    @Expose
    private DTHInfoData dataPA;
    @SerializedName("isVersionValid")
    @Expose
    private String isVersionValid;
    @SerializedName("isAppValid")
    @Expose
    private String isAppValid;

    @SerializedName("dthciData")
    @Expose
    private DthCIData dthciData;


    public DthCIData getDthciData() {
        return dthciData;
    }

    public String getIsVersionValid() {
        return isVersionValid;
    }

    public String getIsAppValid() {
        return isAppValid;
    }

    public String getStatuscode() {
        return statuscode;
    }

    public String getMsg() {
        return msg;
    }

    public DTHInfoData getData() {
        return data;
    }

    public DTHInfoData getDataPA() {
        return dataPA;
    }
}

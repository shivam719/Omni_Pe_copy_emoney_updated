package com.fintech.omnipe.UPIPayment.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetVAResponse {
    @SerializedName("statuscode")
    @Expose
    public int statuscode;
    @SerializedName("msg")
    @Expose
    public String msg;
    @SerializedName("isVersionValid")
    @Expose
    public boolean isVersionValid;
    @SerializedName("isAppValid")
    @Expose
    public boolean isAppValid;
    public UserQRInfo userQRInfo;

    public int getStatuscode() {
        return statuscode;
    }

    public String getMsg() {
        return msg;
    }

    public boolean getVersionValid() {
        return isVersionValid;
    }

    public boolean getAppValid() {
        return isAppValid;
    }

    public UserQRInfo getUserQRInfo() {
        return userQRInfo;
    }
}

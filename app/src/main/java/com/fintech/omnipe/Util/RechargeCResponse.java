package com.fintech.omnipe.Util;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RechargeCResponse {
    @SerializedName("statuscode")
    @Expose
    private String statuscode;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("isVersionValid")
    @Expose
    private String isVersionValid;
    @SerializedName("isAppValid")
    @Expose
    private String isAppValid;

    @SerializedName("commissionAmt")
    @Expose
    private String commissionAmt;


    public String getStatuscode() {
        return statuscode;
    }

    public void setStatuscode(String statuscode) {
        this.statuscode = statuscode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getIsVersionValid() {
        return isVersionValid;
    }

    public String getIsAppValid() {
        return isAppValid;
    }

    public String getCommissionAmt() {
        return commissionAmt;
    }
}

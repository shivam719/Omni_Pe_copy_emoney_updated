package com.fintech.omnipe.UPIPayment.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VPAVerifyResponse {
    @SerializedName("data")
    @Expose
    private VPAVerifyData data;
    int statuscode;
    String msg;
    boolean isVersionValid;


    public VPAVerifyData getData() {
        return data;
    }

    public int getStatuscode() {
        return statuscode;
    }

    public String getMsg() {
        return msg;
    }

    public boolean isVersionValid() {
        return isVersionValid;
    }
}

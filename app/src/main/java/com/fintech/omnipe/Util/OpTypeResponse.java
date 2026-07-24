package com.fintech.omnipe.Util;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OpTypeResponse {

    @SerializedName("data")
    @Expose
    public DataOpType data;
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
    @SerializedName("checkID")
    @Expose
    public int checkID;
    @SerializedName("isPasswordExpired")
    @Expose
    public boolean isPasswordExpired;
    @SerializedName("mobileNo")
    @Expose
    public Object mobileNo;
    @SerializedName("emailID")
    @Expose
    public Object emailID;
    @SerializedName("isDMTWithPipe")
    @Expose
    private boolean isDMTWithPipe;
    @SerializedName("isActive")
    @Expose
    private boolean isActive;
    @SerializedName("isDisplayService")
    @Expose
    private boolean isDisplayService;

    public boolean isActive() {
        return isActive;
    }

    public boolean isDisplayService() {
        return isDisplayService;
    }

    boolean isAddMoneyEnable;
boolean isPaymentGatway;
boolean isUPI,isUPIQR;
boolean isECollectEnable;


    public boolean isVersionValid() {
        return isVersionValid;
    }

    public boolean isAppValid() {
        return isAppValid;
    }

    public boolean isPasswordExpired() {
        return isPasswordExpired;
    }

    public void setData(DataOpType data) {
        this.data = data;
    }

    public boolean isUPIQR() {
        return isUPIQR;
    }

    public DataOpType getData() {
        return data;
    }

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

    public int getCheckID() {
        return checkID;
    }

    public boolean getPasswordExpired() {
        return isPasswordExpired;
    }

    public Object getMobileNo() {
        return mobileNo;
    }

    public Object getEmailID() {
        return emailID;
    }

    public boolean isAddMoneyEnable() {
        return isAddMoneyEnable;
    }

    public boolean isPaymentGatway() {
        return isPaymentGatway;
    }

    public boolean isUPI() {
        return isUPI;
    }

    public boolean isDMTWithPipe() {
        return isDMTWithPipe;
    }

    public boolean isECollectEnable() {
        return isECollectEnable;
    }
}

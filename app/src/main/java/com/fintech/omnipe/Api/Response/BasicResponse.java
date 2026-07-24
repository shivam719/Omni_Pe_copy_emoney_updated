package com.fintech.omnipe.Api.Response;

import com.fintech.omnipe.UPIPayment.dto.UserQRInfo;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BasicResponse {
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

    @SerializedName("isEmailVerified")
    @Expose
    public boolean isEmailVerified;
    @SerializedName("isSocialAlert")
    @Expose
    public boolean isSocialAlert;
    @SerializedName("commRate")
    @Expose
    private double commRate;

    @SerializedName("promotionlist")
    @Expose
    private List<Promotion> promotionlist;
    @SerializedName("data")
    @Expose
    public BasicResponse data;

    public BasicResponse getData() {
        return data;
    }

    public boolean isVersionValid() {
        return isVersionValid;
    }

    public boolean isAppValid() {
        return isAppValid;
    }

    public UserQRInfo getUserQRInfo() {
        return userQRInfo;
    }

    public boolean isEmailVerified() {
        return isEmailVerified;
    }

    public boolean isSocialAlert() {
        return isSocialAlert;
    }

    public double getCommRate() {
        return commRate;
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

    public List<Promotion> getPromotionlist() {
        return promotionlist;
    }

    public void setPromotionlist(List<Promotion> promotionlist) {
        this.promotionlist = promotionlist;
    }
}

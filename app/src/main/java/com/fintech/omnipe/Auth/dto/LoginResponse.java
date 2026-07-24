package com.fintech.omnipe.Auth.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.fintech.omnipe.Api.Object.ChildRoles;
import com.fintech.omnipe.Api.Response.Data;

import java.util.ArrayList;

/**
 * Created by Lalit on 08-03-2017.
 */

public class LoginResponse {
    @SerializedName("childRoles")
    @Expose
    public ArrayList<ChildRoles> childRoles;
    @SerializedName(value = "isAreaMaster",alternate = "IsAreaMaster")
    @Expose
    public boolean isAreaMaster;
    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("otpSession")
    @Expose
    private String otpSession;
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
    @SerializedName("isPasswordExpired")
    @Expose
    private boolean isPasswordExpired;

    @SerializedName("isDTHInfo")
    @Expose
    private boolean isDTHInfo;
    @SerializedName("isRoffer")
    @Expose
    private boolean isRoffer;
    @SerializedName("isHeavyRefresh")
    @Expose
    private boolean isHeavyRefresh;
    @SerializedName("isShowPDFPlan")
    @Expose
    public boolean isShowPDFPlan;

    @SerializedName("isDTHInfoCall")
    @Expose
    public boolean isDTHInfoCall;
    @SerializedName("isRealAPIPerTransaction")
    @Expose
    public boolean isRealAPIPerTransaction;

    @SerializedName("isTargetShow")
    @Expose
    public boolean isTargetShow;
    @SerializedName("isReferral")
    @Expose
    private boolean isReferral;

    @SerializedName("isDenominationIncentive")
    @Expose
    private boolean isDenominationIncentive;
    @SerializedName("isAutoBilling")
    @Expose
    private boolean isAutoBilling;

    @SerializedName("isAdminFlatComm")
    @Expose
    private boolean isAdminFlatComm;

    @SerializedName(value = "isAccountStatement",alternate = "IsAccountStatement")
    @Expose
    public boolean isAccountStatement;
    @SerializedName("isPlanServiceUpdated")
    @Expose
    public boolean isPlanServiceUpdated;

    public boolean isPlanServiceUpdated() {
        return isPlanServiceUpdated;
    }

    public boolean isAreaMaster() {
        return isAreaMaster;
    }

    public boolean isAdminFlatComm() {
        return isAdminFlatComm;
    }

    public boolean isAccountStatement() {
        return isAccountStatement;
    }

    public boolean isDenominationIncentive() {
        return isDenominationIncentive;
    }

    public boolean isAutoBilling() {
        return isAutoBilling;
    }

    public boolean isReferral() {
        return isReferral;
    }

    public boolean isRealAPIPerTransaction() {
        return isRealAPIPerTransaction;
    }

    public boolean isTargetShow() {
        return isTargetShow;
    }

    public boolean isPasswordExpired() {
        return isPasswordExpired;
    }

    public boolean isDTHInfo() {
        return isDTHInfo;
    }

    public boolean isRoffer() {
        return isRoffer;
    }

    public boolean isHeavyRefresh() {
        return isHeavyRefresh;
    }

    public boolean isShowPDFPlan() {
        return isShowPDFPlan;
    }

    public boolean isDTHInfoCall() {
        return isDTHInfoCall;
    }

    public boolean getIsDTHInfo() {
        return isDTHInfo;
    }

    public boolean getIsRoffer() {
        return isRoffer;
    }

    public boolean getIsHeavyRefresh() {
        return isHeavyRefresh;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getIsVersionValid() {
        return isVersionValid;
    }

    public String getIsAppValid() {
        return isAppValid;
    }

    public String getOtpSession() {
        return otpSession;
    }

    public void setOtpSession(String otpSession) {
        this.otpSession = otpSession;
    }

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

    public boolean getIsPasswordExpired() {
        return isPasswordExpired;
    }

    public ArrayList<ChildRoles> getChildRoles() {
        return childRoles;
    }
}

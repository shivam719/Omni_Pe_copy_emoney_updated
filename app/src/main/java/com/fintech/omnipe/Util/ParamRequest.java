package com.fintech.omnipe.Util;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ParamRequest {
    @SerializedName("OTP")
    @Expose
    private String OTP;
    @SerializedName("NewPassword")
    @Expose
    private String NewPassword;

    public ParamRequest(String OTP, String newPassword) {
        this.OTP = OTP;
        this.NewPassword = newPassword;
    }
    public String getOTP() {
        return OTP;
    }

    public String getNewPassword() {
        return NewPassword;
    }
}

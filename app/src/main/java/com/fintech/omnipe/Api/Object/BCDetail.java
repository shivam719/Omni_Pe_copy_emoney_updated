package com.fintech.omnipe.Api.Object;

import com.google.gson.annotations.SerializedName;

public class BCDetail {
    @SerializedName("Mobileno")
    private String Mobileno;
    @SerializedName("secretkey")
    private String secretkey;
    @SerializedName("saltkey")
    private String saltkey;
    @SerializedName("CPID")
    private String CPID;
    @SerializedName("AepsOutletId")
    private String AepsOutletId;
    @SerializedName("EmailId")
    private String EmailId;
    @SerializedName("UserId")
    private String UserId;
    @SerializedName("BCID")
    private String BCID;
    @SerializedName("merchantId")
    private String merchantId;
    @SerializedName("password")
    private String password;

    public String getMobileno() {
        return Mobileno;
    }

    public void setMobileno(String mobileno) {
        Mobileno = mobileno;
    }

    public String getSecretkey() {
        return secretkey;
    }

    public void setSecretkey(String secretkey) {
        this.secretkey = secretkey;
    }

    public String getSaltkey() {
        return saltkey;
    }

    public void setSaltkey(String saltkey) {
        this.saltkey = saltkey;
    }

    public String getCPID() {
        return CPID;
    }

    public void setCPID(String CPID) {
        this.CPID = CPID;
    }

    public String getAepsOutletId() {
        return AepsOutletId;
    }

    public void setAepsOutletId(String aepsOutletId) {
        AepsOutletId = aepsOutletId;
    }

    public String getEmailId() {
        return EmailId;
    }

    public void setEmailId(String emailId) {
        EmailId = emailId;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }


///AEPS2

    public String getBCID() {
        return BCID;
    }

    public void setBCID(String BCID) {
        this.BCID = BCID;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

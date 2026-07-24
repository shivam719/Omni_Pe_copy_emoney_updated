package com.fintech.omnipe.Api.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BenisObject {
    private String mobileNo;
    private String ifsc;
    private String accountNo;
    private String bankName;
    private String bankID;
    private String beneName;
    private String beneID;
    @SerializedName("isVerified")
    @Expose
    private boolean isVerified;


    public boolean isVerified() {
        return isVerified;
    }

    public String getBeneName() {
        return beneName;
    }

    public void setBeneName(String beneName) {
        this.beneName = beneName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getIfsc() {
        return ifsc;
    }

    public void setIfsc(String ifsc) {
        this.ifsc = ifsc;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankID() {
        return bankID;
    }

    public void setBankID(String bankID) {
        this.bankID = bankID;
    }

    public String getBeneID() {
        return beneID;
    }

    public void setBeneID(String beneID) {
        this.beneID = beneID;
    }
}

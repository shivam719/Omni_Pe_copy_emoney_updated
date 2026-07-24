package com.fintech.omnipe.Util;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PaymentMode implements Serializable {

    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("bankID")
    @Expose
    public int bankID;
    @SerializedName("modeID")
    @Expose
    public int modeID;
    @SerializedName("isActive")
    @Expose
    public boolean isActive;
    @SerializedName("mode")
    @Expose
    public String mode;
    @SerializedName("isTransactionIdAuto")
    @Expose
    public boolean isTransactionIdAuto;
    @SerializedName("isAccountHolderRequired")
    @Expose
    public boolean isAccountHolderRequired;
    @SerializedName("isChequeNoRequired")
    @Expose
    public boolean isChequeNoRequired;
    @SerializedName("isCardNumberRequired")
    @Expose
    public boolean isCardNumberRequired;
    @SerializedName("isMobileNoRequired")
    @Expose
    public boolean isMobileNoRequired;
    @SerializedName("isBranchRequired")
    @Expose
    public boolean isBranchRequired;
    @SerializedName("isUPIID")
    @Expose
    public boolean isUPIID;
    @SerializedName("cid")
    @Expose
    public String cid;

    public int getId() {
        return id;
    }

    public int getModeID() {
        return modeID;
    }

    public int getBankID() {
        return bankID;
    }

    public void setBankID(int bankID) {
        this.bankID = bankID;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean getIsBranchRequired() {
        return isBranchRequired;
    }

    public void setBranchRequired(boolean branchRequired) {
        isBranchRequired = branchRequired;
    }

    public boolean getIsUPIID() {
        return isUPIID;
    }

    public void setUPIID(boolean UPIID) {
        isUPIID = UPIID;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public boolean getIsTransactionIdAuto() {
        return isTransactionIdAuto;
    }

    public void setIsTransactionIdAuto(boolean isTransactionIdAuto) {
        this.isTransactionIdAuto = isTransactionIdAuto;
    }

    public boolean getIsAccountHolderRequired() {
        return isAccountHolderRequired;
    }

    public void setIsAccountHolderRequired(boolean isAccountHolderRequired) {
        this.isAccountHolderRequired = isAccountHolderRequired;
    }

    public boolean getIsChequeNoRequired() {
        return isChequeNoRequired;
    }

    public void setIsChequeNoRequired(boolean isChequeNoRequired) {
        this.isChequeNoRequired = isChequeNoRequired;
    }

    public boolean getIsCardNumberRequired() {
        return isCardNumberRequired;
    }

    public void setIsCardNumberRequired(boolean isCardNumberRequired) {
        this.isCardNumberRequired = isCardNumberRequired;
    }

    public boolean getIsMobileNoRequired() {
        return isMobileNoRequired;
    }

    public void setIsMobileNoRequired(boolean isMobileNoRequired) {
        this.isMobileNoRequired = isMobileNoRequired;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

}
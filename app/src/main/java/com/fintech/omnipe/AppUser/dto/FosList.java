package com.fintech.omnipe.AppUser.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class FosList {

    @SerializedName("userReports")
    @Expose
    private ArrayList<UserReport> userReports = null;
    @SerializedName("canEdit")
    @Expose
    private boolean canEdit;
    @SerializedName("canAssignPackage")
    @Expose
    private boolean canAssignPackage;
    @SerializedName("canVerifyDocs")
    @Expose
    private boolean canVerifyDocs;
    @SerializedName("canFundTransfer")
    @Expose
    private boolean canFundTransfer;
    @SerializedName("canChangeUserStatus")
    @Expose
    private boolean canChangeUserStatus;
    @SerializedName("canChangeOTPStatus")
    @Expose
    private boolean canChangeOTPStatus;
    @SerializedName("canChangeSlab")
    @Expose
    private boolean canChangeSlab;
    @SerializedName("canChangeRole")
    @Expose
    private boolean canChangeRole;
    @SerializedName("canAssignAvailablePackage")
    @Expose
    private boolean canAssignAvailablePackage;
    @SerializedName("loginID")
    @Expose
    private String loginID;
    @SerializedName("rowCount")
    @Expose
    private String rowCount;
    @SerializedName("pegeSetting")
    @Expose
    private PegeSetting pegeSetting;
    @SerializedName("canRegeneratePassword")
    @Expose
    private boolean canRegeneratePassword;

    public ArrayList<UserReport> getUserReports() {
        return userReports;
    }

    public void setUserReports(ArrayList<UserReport> userReports) {
        this.userReports = userReports;
    }

    public boolean getCanEdit() {
        return canEdit;
    }

    public void setCanEdit(boolean canEdit) {
        this.canEdit = canEdit;
    }

    public boolean getCanAssignPackage() {
        return canAssignPackage;
    }

    public void setCanAssignPackage(boolean canAssignPackage) {
        this.canAssignPackage = canAssignPackage;
    }

    public boolean getCanVerifyDocs() {
        return canVerifyDocs;
    }

    public void setCanVerifyDocs(boolean canVerifyDocs) {
        this.canVerifyDocs = canVerifyDocs;
    }

    public boolean getCanFundTransfer() {
        return canFundTransfer;
    }

    public void setCanFundTransfer(boolean canFundTransfer) {
        this.canFundTransfer = canFundTransfer;
    }

    public boolean getCanChangeUserStatus() {
        return canChangeUserStatus;
    }

    public void setCanChangeUserStatus(boolean canChangeUserStatus) {
        this.canChangeUserStatus = canChangeUserStatus;
    }

    public boolean getCanChangeOTPStatus() {
        return canChangeOTPStatus;
    }

    public void setCanChangeOTPStatus(boolean canChangeOTPStatus) {
        this.canChangeOTPStatus = canChangeOTPStatus;
    }

    public boolean getCanChangeSlab() {
        return canChangeSlab;
    }

    public void setCanChangeSlab(boolean canChangeSlab) {
        this.canChangeSlab = canChangeSlab;
    }

    public boolean getCanChangeRole() {
        return canChangeRole;
    }

    public void setCanChangeRole(boolean canChangeRole) {
        this.canChangeRole = canChangeRole;
    }

    public boolean getCanAssignAvailablePackage() {
        return canAssignAvailablePackage;
    }

    public void setCanAssignAvailablePackage(boolean canAssignAvailablePackage) {
        this.canAssignAvailablePackage = canAssignAvailablePackage;
    }

    public String getLoginID() {
        return loginID;
    }

    public void setLoginID(String loginID) {
        this.loginID = loginID;
    }

    public String getRowCount() {
        return rowCount;
    }

    public void setRowCount(String rowCount) {
        this.rowCount = rowCount;
    }

    public PegeSetting getPegeSetting() {
        return pegeSetting;
    }

    public void setPegeSetting(PegeSetting pegeSetting) {
        this.pegeSetting = pegeSetting;
    }

    public boolean getCanRegeneratePassword() {
        return canRegeneratePassword;
    }

    public void setCanRegeneratePassword(boolean canRegeneratePassword) {
        this.canRegeneratePassword = canRegeneratePassword;
    }
}

package com.fintech.omnipe.Util;

public class RefundRequestResponse {

    public String statuscode, msg, checkID, mobileNo, emailID;
    public boolean isVersionValid, isAppValid, isPasswordExpired;

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

    public String getCheckID() {
        return checkID;
    }

    public void setCheckID(String checkID) {
        this.checkID = checkID;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getEmailID() {
        return emailID;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

    public boolean isVersionValid() {
        return isVersionValid;
    }

    public void setVersionValid(boolean versionValid) {
        isVersionValid = versionValid;
    }

    public boolean isAppValid() {
        return isAppValid;
    }

    public void setAppValid(boolean appValid) {
        isAppValid = appValid;
    }

    public boolean isPasswordExpired() {
        return isPasswordExpired;
    }

    public void setPasswordExpired(boolean passwordExpired) {
        isPasswordExpired = passwordExpired;
    }


}



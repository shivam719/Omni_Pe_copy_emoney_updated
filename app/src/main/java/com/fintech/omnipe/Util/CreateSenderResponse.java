package com.fintech.omnipe.Util;

public class CreateSenderResponse {
    private boolean isSenderNotExists;
    private boolean isEKYCAvailable;
    private boolean isOTPGenerated;
    private boolean isOTPRequired;
    private double remainingLimit;
    private double availbleLimit;
    private String senderName;
    private String sid;
    private String senderBalance;
    private String statuscode;
    private String msg;
    private String isVersionValid;
    private String isAppValid;

    public boolean isSenderNotExists() {
        return isSenderNotExists;
    }

    public boolean isEKYCAvailable() {
        return isEKYCAvailable;
    }

    public boolean isOTPGenerated() {
        return isOTPGenerated;
    }

    public boolean isOTPRequired() {
        return isOTPRequired;
    }

    public double getRemainingLimit() {
        return remainingLimit;
    }

    public double getAvailbleLimit() {
        return availbleLimit;
    }

    public String getSenderName() {
        return senderName;
    }

    public String getSid() {
        return sid;
    }

    public String getSenderBalance() {
        return senderBalance;
    }

    public String getStatuscode() {
        return statuscode;
    }

    public String getMsg() {
        return msg;
    }

    public String getIsVersionValid() {
        return isVersionValid;
    }

    public String getIsAppValid() {
        return isAppValid;
    }
}

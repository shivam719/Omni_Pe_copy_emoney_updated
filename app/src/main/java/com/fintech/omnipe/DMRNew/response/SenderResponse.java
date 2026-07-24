package com.fintech.omnipe.DMRNew.response;


import com.fintech.omnipe.DMRNew.Data.DMTData;

public class SenderResponse {
    private boolean isSenderNotExists;
    private boolean isEKYCAvailable;
    private boolean isOTPGenerated;
    private boolean isOTPRequired;
    private String sid;
    private String senderName;
    private String beneName;
    private String senderBalance;
    private int statuscode;
    private String msg;
    private boolean isVersionValid;
    private boolean isAppValid;
    String remainingLimit, availbleLimit;
    DMTData data;

    public boolean isSenderNotExists() {
        return isSenderNotExists;
    }

    public String getSenderName() {
        return senderName != null ? senderName : "";
    }

    public String getSenderBalance() {
        return senderBalance;
    }

    public int getStatuscode() {
        return statuscode;
    }

    public String getMsg() {
        return msg;
    }

    public boolean isVersionValid() {
        return isVersionValid;
    }

    public boolean isAppValid() {
        return isAppValid;
    }

    public String getRemainingLimit() {
        return remainingLimit;
    }

    public String getAvailbleLimit() {
        return availbleLimit;
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

    public String getBeneName() {
        return beneName;
    }

    public String getSid() {
        return sid;
    }

    public DMTData getData() {
        return data;
    }
}

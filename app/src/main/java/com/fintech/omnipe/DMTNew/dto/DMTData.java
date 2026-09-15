package com.fintech.omnipe.DMTNew.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DMTData {
    @SerializedName("firstName")
    @Expose
    public String firstName;
    @SerializedName("lastName")
    @Expose
    public String lastName;
    @SerializedName("mobile")
    @Expose
    public String mobile;
    @SerializedName(value = "referenceID",alternate = "refferenceID")
    @Expose
    public String referenceID;
    @SerializedName("limit")
    @Expose
    public double limit;
    @SerializedName("isOTPRequired")
    @Expose
    public boolean isOTPRequired;
    @SerializedName("isBioMetricRequired")
    @Expose
    public boolean isBioMetricRequired;
    @SerializedName("senderStatus")
    @Expose
    public int senderStatus;
    @SerializedName("tid")
    @Expose
    public int tid;
    @SerializedName("liveID")
    @Expose
    public String liveID;
    @SerializedName("transactionID")
    @Expose
    public String transactionID;
    @SerializedName("beneName")
    @Expose
    public String beneName;
    @SerializedName("bank")
    @Expose
    public String bank;
    @SerializedName("groupID")
    @Expose
    public String groupID;
    @SerializedName("balance")
    @Expose
    public double balance;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMobile() {
        return mobile;
    }

    public double getLimit() {
        return limit;
    }

    public boolean isBioMetricRequired() {
        return isBioMetricRequired;
    }

    public boolean isOTPRequired() {
        return isOTPRequired;
    }

    public String getReferenceID() {
        return referenceID;
    }

    public int getSenderStatus() {
        return senderStatus;
    }

    public void setSenderStatus(int senderStatus) {
        this.senderStatus = senderStatus;
    }

    public int getTid() {
        return tid;
    }

    public String getLiveID() {
        return liveID;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public String getBeneName() {
        return beneName;
    }

    public String getBank() {
        return bank;
    }

    public double getBalance() {
        return balance;
    }

    public String getGroupID() {
        return groupID;
    }
}

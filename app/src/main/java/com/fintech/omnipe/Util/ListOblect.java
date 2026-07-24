package com.fintech.omnipe.Util;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListOblect {

    @SerializedName("requestedAmount")
    @Expose
    private Integer requestedAmount;
    @SerializedName("liveID")
    @Expose
    private String liveID;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("statuscode")
    @Expose
    private Integer statuscode;
    @SerializedName("transactionID")
    @Expose
    private String transactionID;
    @SerializedName("tid")
    @Expose
    private Integer tid;

    public Integer getRequestedAmount() {
        return requestedAmount;
    }

    public void setRequestedAmount(Integer requestedAmount) {
        this.requestedAmount = requestedAmount;
    }

    public String getLiveID() {
        return liveID;
    }

    public void setLiveID(String liveID) {
        this.liveID = liveID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getStatuscode() {
        return statuscode;
    }

    public void setStatuscode(Integer statuscode) {
        this.statuscode = statuscode;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

}
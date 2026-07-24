package com.fintech.omnipe.Activities.DthPlan.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class PlanInfoData implements Serializable {

    @SerializedName(value = "records",alternate = "rdata")
    @Expose
    private PlanInfoRecords records;
    @SerializedName(value = "status", alternate = "error")
    @Expose
    private String status;
    @SerializedName("result")
    @Expose
    private ResultRes result;
    @SerializedName("msg")
    @Expose
    private String msg;

    @SerializedName("pType")
    @Expose
    private String pType;
    @SerializedName("pDetials")
    @Expose
    private List<PDetails> pDetials = null;


    public String getpType() {
        return pType;
    }

    public List<PDetails> getpDetials() {
        return pDetials;
    }

    public PlanInfoRecords getRecords() {
        return records;
    }

    public void setRecords(PlanInfoRecords records) {
        this.records = records;
    }

    public String getStatus() {
        return status;
    }

    public ResultRes getResult() {
        return result;
    }

    public String getMsg() {
        return msg;
    }



}

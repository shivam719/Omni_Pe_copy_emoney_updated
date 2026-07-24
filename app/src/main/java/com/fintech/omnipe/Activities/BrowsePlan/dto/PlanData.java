package com.fintech.omnipe.Activities.BrowsePlan.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PlanData {

    @SerializedName("status")
    @Expose
    private int status;
    @SerializedName("time")
    @Expose
    private String time;

    @SerializedName("error")
    @Expose
    private int error;
    @SerializedName("operator")
    @Expose
    private String operator;
    @SerializedName("circle")
    @Expose
    private String circle;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName(value = "records",alternate = "rdata")
    @Expose
    PlanDataRecords records;

    @SerializedName("types")
    @Expose
    private ArrayList<PlanTypes> types;

    public ArrayList<PlanTypes> getTypes() {
        return types;
    }

    public int getError() {
        return error;
    }

    public String getOperator() {
        return operator;
    }

    public String getCircle() {
        return circle;
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }

    public String getTime() {
        return time;
    }

    public PlanDataRecords getRecords() {
        return records;
    }
}

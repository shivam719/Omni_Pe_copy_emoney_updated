package com.fintech.omnipe.Activities.DthPlan.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ResultRes implements Serializable {

    @SerializedName("records")
    @Expose
    private PlanInfoRecords records;

    public PlanInfoRecords getRecords() {
        return records;
    }
}

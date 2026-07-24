package com.fintech.omnipe.Api.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IncentiveDetails {
    @SerializedName("denomination")
    @Expose
    int denomination;
    @SerializedName("comm")
    @Expose
    double comm;
    @SerializedName("amtType")
    @Expose
    boolean amtType;

    public int getDenomination() {
        return denomination;
    }

    public double getComm() {
        return comm;
    }

    public boolean isAmtType() {
        return amtType;
    }
}

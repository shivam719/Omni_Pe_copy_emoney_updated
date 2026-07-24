package com.fintech.omnipe.Api.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BillAmountOptionInfo implements Serializable {
    @SerializedName("amountName")
    @Expose
    public String amountName;
    @SerializedName("amountValue")
    @Expose
    public double amountValue;

    public String getAmountName() {
        return amountName;
    }

    public double getAmountValue() {
        return amountValue;
    }
}

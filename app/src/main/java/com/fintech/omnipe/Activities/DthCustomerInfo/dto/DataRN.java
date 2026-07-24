package com.fintech.omnipe.Activities.DthCustomerInfo.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DataRN implements Serializable {

    @SerializedName("key")
    @Expose
    private String key;
    @SerializedName("value")
    @Expose
    private String value;

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}

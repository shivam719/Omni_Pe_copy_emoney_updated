package com.fintech.omnipe.Activities.DthCustomerInfo.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class DthCIData implements Serializable {

    @SerializedName("statusCode")
    @Expose
    private Integer statusCode;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private List<DataRN> data = null;

    public Integer getStatusCode() {
        return statusCode;
    }

    public String getMsg() {
        return msg;
    }

    public List<DataRN> getData() {
        return data;
    }
}

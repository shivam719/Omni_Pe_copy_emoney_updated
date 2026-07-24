package com.fintech.omnipe.Api.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OperatorOptionalResponse {
    @SerializedName("data")
    @Expose
    public Data data;
    @SerializedName("statuscode")
    @Expose
    public Integer statuscode;
    @SerializedName("msg")
    @Expose
    public String msg;
    @SerializedName("isVersionValid")
    @Expose
    public Boolean isVersionValid;
    @SerializedName("isAppValid")
    @Expose
    public Boolean isAppValid;
    @SerializedName("checkID")
    @Expose
    public Integer checkID;

    public Data getData() {
        return data;
    }

    public Integer getStatuscode() {
        return statuscode;
    }

    public String getMsg() {
        return msg;
    }

    public Boolean getVersionValid() {
        return isVersionValid;
    }

    public Boolean getAppValid() {
        return isAppValid;
    }

    public Integer getCheckID() {
        return checkID;
    }
}

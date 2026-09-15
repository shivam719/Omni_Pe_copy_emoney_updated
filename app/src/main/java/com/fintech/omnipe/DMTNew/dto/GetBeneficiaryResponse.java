package com.fintech.omnipe.DMTNew.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.fintech.omnipe.Api.Response.BenisObject;

import java.util.ArrayList;

public class GetBeneficiaryResponse {
    @SerializedName("sid")
    @Expose
    private String sid;
    @SerializedName("statuscode")
    @Expose
    private int statuscode;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("isVersionValid")
    @Expose

    private boolean isVersionValid;
    @SerializedName("isAppValid")
    @Expose
    private boolean isAppValid;
    @SerializedName("data")
    @Expose
    ArrayList<BenisObject> data;

    public String getSid() {
        return sid;
    }

    public int getStatuscode() {
        return statuscode;
    }

    public String getMsg() {
        return msg;
    }

    public boolean isVersionValid() {
        return isVersionValid;
    }

    public boolean isAppValid() {
        return isAppValid;
    }

    public ArrayList<BenisObject> getData() {
        return data;
    }


}

package com.fintech.omnipe.AccountSettlement.API;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SettlementAccountResponse {

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
    private List<SettlementAccountData> dataList;

    @SerializedName("isSattlemntAccountVerify")
    @Expose
    private boolean isSattlemntAccountVerify;


    public boolean isSattlemntAccountVerify() {
        return isSattlemntAccountVerify;
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

    public List<SettlementAccountData> getDataList() {
        return dataList;
    }
}

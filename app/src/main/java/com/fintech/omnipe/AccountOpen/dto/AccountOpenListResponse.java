package com.fintech.omnipe.AccountOpen.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AccountOpenListResponse {

    @SerializedName("accountOpeningDeatils")
    @Expose
    public ArrayList<AccountOpData> accountOpeningDeatils;

    @SerializedName("statuscode")
    @Expose
    public int statuscode;
    @SerializedName("msg")
    @Expose
    public String msg;
    @SerializedName("isVersionValid")
    @Expose
    public boolean isVersionValid;
    @SerializedName("isAppValid")
    @Expose
    public boolean isAppValid;
    @SerializedName("isDrawOpImage")
    @Expose
    public boolean isDrawOpImage;

    public boolean isDrawOpImage() {
        return isDrawOpImage;
    }
    public ArrayList<AccountOpData> getAccountOpeningDeatils() {
        return accountOpeningDeatils;
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

}

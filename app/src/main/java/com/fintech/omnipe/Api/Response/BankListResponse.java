package com.fintech.omnipe.Api.Response;

import com.fintech.omnipe.Api.Object.BankListObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;



public class BankListResponse implements Serializable {

    private String statuscode;
    private String msg;
    private String isVersionValid;
    private String isAppValid;
    @SerializedName("banks")
    @Expose
    private ArrayList<BankListObject> banks;
    @SerializedName("bankMasters")
    @Expose
    private ArrayList<BankListObject> bankMasters;

    @SerializedName("aepsBanks")
    @Expose
    private ArrayList<BankListObject> aepsBanks;

    public ArrayList<BankListObject> getBanks() {
        return banks;
    }

    public ArrayList<BankListObject> getAepsBanks() {
        return aepsBanks;
    }

    public String getIsVersionValid() {
        return isVersionValid;
    }

    public String getIsAppValid() {
        return isAppValid;
    }

    public String getStatuscode() {
        return statuscode;
    }

    public void setStatuscode(String statuscode) {
        this.statuscode = statuscode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ArrayList<BankListObject> getBankMasters() {
        return bankMasters;
    }



}
